package com.goeuro.core.inner;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {

	private static final Logger log = LoggerFactory.getLogger(Parser.class);
	private final String path;
	private Thread thread;

	public Parser(String path) {
		this.path = path;
	}

	/**
	 * is parsing done
	 */
	public boolean isDone() {
		return !this.thread.isAlive();
	}

	/**
	 * Parse and store in to supplied data structure
	 * 
	 * @param dataStructure
	 *            {@link BusRouteDataStructure}
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	public void parse(BusRouteDataStructure dataStructure) {
		thread = new Thread(new Parse(dataStructure));
		thread.run();
	}

	private class Parse implements Runnable {

		private BusRouteDataStructure dataStructure;

		Parse(BusRouteDataStructure dataStructure) {
			this.dataStructure = dataStructure;
		}

		@Override
		public void run() {
			if (!(new File(Parser.this.path)).exists()) {
				log.error("File not found on given path!");
				return;
			}

			try (Stream<String> fileStream = Files.lines(Paths.get(Parser.this.path))) {
				fileStream.skip(1).forEach(line -> {
					if (line.trim().length() > 0) {
						String[] items = line.split("\\s+");
						int busRouteId = Integer.parseInt(items[0]);
						IntStream.range(1, items.length).forEach(i -> {
							dataStructure.addRouteInStation(busRouteId, Integer.parseInt(items[i]), i);
						});
					}
				});
			} catch (NumberFormatException numEx) {
				log.error("can not parse in to Integer", numEx);
			} catch (Exception ex) {
				log.error("Error occured while parsing file.", ex);
			}

		}

	}

}
