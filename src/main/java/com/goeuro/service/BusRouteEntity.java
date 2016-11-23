package com.goeuro.service;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for Bus route service response
 * @author arun
 *
 */
public class BusRouteEntity {

	private int dep_sid;
	
	private int arr_sid;
	
	private boolean direct_bus_route;
	
	/**
	 * default constructor
	 */
	public BusRouteEntity(){
		//
	}

	/**
	 *  Constructor
	 * @param dep_sid departure station id
	 * @param arr_sid arrival station id
	 */
	public BusRouteEntity(int dep_sid, int arr_sid) {
		this.dep_sid = dep_sid;
		this.arr_sid = arr_sid;
		this.direct_bus_route = false;
	}

	/**
	 * get Departure id
	 * @return
	 */
	@JsonProperty
	public int getDep_sid() {
		return dep_sid;
	}

	/**
	 * get Arrival StationId
	 * @return
	 */
	@JsonProperty
	public int getArr_sid() {
		return arr_sid;
	}

	/**
	 * get direct bus root exist between {@link #getDep_sid()} and {@link #getArr_sid()}
	 * @return
	 */
	@JsonProperty
	public boolean isDirect_bus_route() {
		return direct_bus_route;
	}

	/**
	 * 
	 * @param direct_bus_route
	 */
	public void setDirect_bus_route(final boolean direct_bus_route) {
		this.direct_bus_route = direct_bus_route;
	}

}
