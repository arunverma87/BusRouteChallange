package com.goeuro.core.inner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BusRouteDataStructure {

	Map<Integer, Station> stations;

	/**
	 * Constructor
	 */
	public BusRouteDataStructure() {
		stations = new ConcurrentHashMap<>();
	}

	/**
	 * Add route in station's in routes list
	 * 
	 * @param routeId
	 *            route id
	 * @param stationId
	 *            station id
	 * @param locationIndex
	 *            location index
	 */
	public void addRouteInStation(int routeId, int stationId, int locationIndex) {

		if (stations.containsKey(stationId))
			stations.get(stationId).addRoutes(routeId, locationIndex);
		else {
			Station st = new Station(stationId);
			st.addRoutes(routeId, locationIndex);
			stations.put(stationId, st);
		}
	}

	/**
	 * get all stations
	 * 
	 * @return Map of stations
	 */
	public Map<Integer, Station> getAllStations() {
		return stations;
	}

	/**
	 * get station object for supplied station id
	 * 
	 * @param stationId
	 *            station id
	 * @return {@link Station} or <code>null</code>
	 */
	public Station getStationById(int stationId) {
		return isStationExist(stationId) ? stations.get(stationId) : null;
	}

	/**
	 * Is station exist?
	 * 
	 * @param stationId
	 *            station id
	 * @return true if exist, else false
	 */
	public boolean isStationExist(int stationId) {
		return stations.containsKey(stationId);
	}

	/**
	 * @param station
	 * @return
	 */
	public Map<Integer, Integer> getStationRoutes(Station station) {
		return getStationRoutes(station.getId());
	}

	/**
	 * @param station
	 * @return Map of Routes and location in that route or <code>null</code>
	 */
	public Map<Integer, Integer> getStationRoutes(int stationId) {
		Station st = getStationById(stationId);
		return st != null ? st.getRoutes() : null;
	}

}
