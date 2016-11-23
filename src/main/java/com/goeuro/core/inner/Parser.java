package com.goeuro.core.inner;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {

	private static final Logger log = LoggerFactory.getLogger(Parser.class);
	private final String path;
	private ExecutorService executor;

	public Parser(String path) {
		this.path = path;
		this.executor = Executors.newSingleThreadExecutor();
	}

	/**
	 * is parsing done
	 */
	public boolean isDone() {
		return this.executor.isShutdown();
	}

	/**
	 * Parse and store in to supplied data structure
	 * 
	 * @param dataStructure {@link BusRouteDataStructure}
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	public void parse(BusRouteDataStructure dataStructure) {

		Future<Boolean> task = executor.submit(() -> {
			if (!(new File(this.path)).exists()) {
				log.error("File not found on given path!");
				return false;
			}

			try (Stream<String> fileStream = Files.lines(Paths.get(this.path))) {
				fileStream.skip(1).forEach(line -> {
					if (line.trim().length() > 0) {
						String[] items = line.split("\\s+");
						int busRouteId = Integer.parseInt(items[0]);
						IntStream.range(1, items.length).forEach(i -> {
							dataStructure.addRouteInStation(busRouteId, Integer.parseInt(items[i]), i);
						});
					}
				});
				return true;
			} catch (NumberFormatException numEx) {
				log.error("can not parse in to Integer", numEx);
				return false;
			} catch (Exception ex) {
				log.error("Error occured while parsing file.", ex);
				return false;
			}
		});

		try {
			task.get();			
			this.executor.shutdown();
			this.executor.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			this.executor.shutdownNow();
		} catch (ExecutionException e) {
			this.executor.shutdownNow();
		}		
	}
}
