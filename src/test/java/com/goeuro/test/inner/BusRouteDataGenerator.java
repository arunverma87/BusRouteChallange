package com.goeuro.test.inner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class BusRouteDataGenerator {

	private final static int ROUTES = 10000;
	private final static int STATIONS = 1000000;
	private final static int STATIONS_IN_ROUTE = 1000;
	
	public static void main(String[] args) throws IOException {
		BusRouteDataGenerator generator = new BusRouteDataGenerator();
		generator.generateData();
	}

	public void generateData() throws IOException {

		Path data = Files.createTempFile("busroutedata", ".txt");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(data.toFile()))) {
			String line = "";
			writeLine(Integer.toString(ROUTES), bw);

			for (int route = 1; route <= ROUTES; route++) {
				Random randStation = new Random();
				line += Integer.toString(route);
				for (int location = 1; location <= STATIONS_IN_ROUTE; location++) {
					line += " " + randStation.nextInt(STATIONS);
				}
				writeLine(line, bw);
				line = "";
			}

		} catch (Exception ex) {

		}
	}

	private static void writeLine(String line, BufferedWriter bw) throws IOException {
		bw.write(line);
		bw.newLine();
	}
}
