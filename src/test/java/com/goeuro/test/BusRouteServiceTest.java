package com.goeuro.test;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.goeuro.core.BusRouteDataContainer;
import com.goeuro.service.util.BusRouteFinder;

public class BusRouteServiceTest {

	private static String RESOURCE_DATA_FILE;

	@Test
	public void testBusRouteParsing() throws IOException, InterruptedException, ExecutionException {
		RESOURCE_DATA_FILE = "busroutedata.txt";

		// copy to temp file
		Path filePath = copyToTempFile();

		BusRouteDataContainer container = BusRouteDataContainer.getInstance();

		container.createDataStructureFromFile(filePath.toFile().getAbsolutePath());

		Assert.assertFalse(container.isEmpty());
		int stationId = 114;
		Assert.assertFalse(container.getBusRoutes(stationId).isEmpty());
		Assert.assertEquals(5, container.getBusRoutes(stationId).size());

		Assert.assertFalse(directRoute(114, 153));
		Assert.assertTrue(directRoute(153, 114));
		Assert.assertTrue(directRoute(19, 184));
		Assert.assertFalse(directRoute(184, 19));
		Assert.assertTrue(directRoute(184, 184));
		Assert.assertTrue(directRoute(184, 184));
	}


	private boolean directRoute(int arr_id, int dep_id) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Boolean> finder = executor.submit(new BusRouteFinder(arr_id, dep_id));
		boolean isDirectRoute = finder.get();
		executor.shutdown();
		executor.awaitTermination(500, TimeUnit.MILLISECONDS);
		return isDirectRoute;
	}

	private Path copyToTempFile() throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(RESOURCE_DATA_FILE);
		Path tempFilePath = Files.createTempFile("data", ".txt");
		tempFilePath.toFile().deleteOnExit();
		int readBytes;
		try (BufferedInputStream br = new BufferedInputStream(is);
				BufferedWriter writer = Files.newBufferedWriter(tempFilePath)) {
			while ((readBytes = is.read()) != -1) {
				writer.write((char) readBytes);
			}
		}
		return tempFilePath;
	}
}
