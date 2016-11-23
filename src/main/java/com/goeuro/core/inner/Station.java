package com.goeuro.core.inner;

import java.util.HashMap;
import java.util.Map;

/**
 * class represents a Station
 * @author arun
 *
 */
public class Station {
	
	private final int stationId;

	private final Map<Integer, Integer> inRoutes;
	
	/**
	 * Constructor
	 */
	public Station(int stationId) {
		inRoutes = new HashMap<>();
		this.stationId= stationId;
	}
	
	/**
	 * Add routes for station
	 * @param routeId route id
	 * @param locationIndex location of station id in route 
	 */
	public void addRoutes(int routeId, int locationIndex){
		inRoutes.put(routeId, locationIndex);
	}
	
	/**
	 * Get list of routes in which station occur
	 * @return list of routes with <Route Id, Location Id>
	 */
	public Map<Integer, Integer> getRoutes(){
		return inRoutes;
	}
	
	/**
	 * get station id
	 * @return id
	 */
	public int getId(){
		return this.stationId;
	}
}
