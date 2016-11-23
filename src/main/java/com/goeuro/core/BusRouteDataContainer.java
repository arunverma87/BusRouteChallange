package com.goeuro.core;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.goeuro.core.inner.BusRouteDataStructure;
import com.goeuro.core.inner.Parser;
import com.goeuro.core.inner.Station;

/**
 * Bus route data container with helpful methods
 * 
 * @author arunv
 *
 */
public class BusRouteDataContainer {

	private static BusRouteDataStructure DATA;
	private Parser parser;

	private static BusRouteDataContainer container = null;

	/**
	 * get Instance
	 * 
	 * @return a object of BusRouteDataContainer
	 */
	public static synchronized BusRouteDataContainer getInstance() {
		if (container == null)
			container = new BusRouteDataContainer();
		return container;
	}

	private BusRouteDataContainer() {
		DATA = new BusRouteDataStructure();
	}

	/**
	 * parse from file
	 * 
	 * @param filePath
	 *            file path
	 * @throws FileNotFoundException
	 *             file not found exception
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void createDataStructureFromFile(String filePath) throws FileNotFoundException {
		parser = new Parser(filePath);
		parser.parse(DATA);
	}
	
	public void setData(BusRouteDataStructure data){
		DATA = data;
	}

	public boolean isParsingDone() {
		return parser.isDone();
	}

	/**
	 * get all stations from data structure
	 * 
	 * @return Map of Stations
	 */
	public Map<Integer, Station> getAllStation() {
		return DATA.getAllStations();
	}

	/**
	 * get empty property of container
	 * 
	 * @return true, if container is empty or false
	 */
	public boolean isEmpty() {
		return getAllStation().isEmpty();
	}

	/**
	 * Is station exists
	 * 
	 * @param stationId
	 *            station id
	 * @return boolean
	 */
	public boolean isStationExist(int stationId) {
		return DATA.isStationExist(stationId);
	}

	/**
	 * get bus routes from station id
	 * 
	 * @param stationId
	 *            station id
	 * @return Map of routes and location index
	 */
	public Map<Integer, Integer> getBusRoutes(int stationId) {
		return DATA.getStationRoutes(stationId);
	}

	/**
	 * get bus routes with location index of the specified station
	 * 
	 * @param station
	 *            {@link Station}
	 * @return Map of routes and location index
	 */
	public Map<Integer, Integer> getBusRoutes(Station station) {
		return DATA.getStationRoutes(station);
	}

}
