package com.goeuro.service.util;

import java.util.Map;
import java.util.concurrent.Callable;

import com.goeuro.core.BusRouteDataContainer;

/**
 * Utility class to find bus route
 * 
 * @author arun
 *
 */
public class BusRouteFinder implements Callable<Boolean> {

	private final int departureId, arrivalId;

	private final BusRouteDataContainer container;

	/**
	 * Constructor
	 * 
	 * @param dep_sid
	 *            departure id
	 * @param arr_sid
	 *            arrival id
	 */
	public BusRouteFinder(final int dep_sid, final int arr_sid) {
		this.departureId = dep_sid;
		this.arrivalId = arr_sid;
		container = BusRouteDataContainer.getInstance();
	}

	@Override
	public Boolean call() throws Exception {

		if (!areStationsExist())
			return false;

		if (this.departureId == this.arrivalId)
			return true;

		Map<Integer, Integer> routesOfDepStationId = container.getBusRoutes(this.departureId);
		Map<Integer, Integer> routesOfArrStationId = container.getBusRoutes(this.arrivalId);

		boolean directRoute = routesOfDepStationId.entrySet().stream()
				.anyMatch(obj -> (routesOfArrStationId.get(obj.getKey()) == null ? -1
						: routesOfArrStationId.get(obj.getKey())) > obj.getValue());
		return directRoute;
	}

	private boolean areStationsExist() {
		// Will check for station existence for 5 second then return false if it
		// does not appear in data structure
		final int exitCounter = 50;
		int counter = 0;
		while (counter < exitCounter) {
			if (container.isParsingDone()) {
				if (isStationExit(departureId) && isStationExit(arrivalId))
					return true;
				else
					return false;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				//
			}
			counter++;
		}

		return false;
	}

	private boolean isStationExit(int stationId) {
		return container.isStationExist(stationId);
	}
}
