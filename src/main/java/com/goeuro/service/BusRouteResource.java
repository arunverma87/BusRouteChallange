package com.goeuro.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goeuro.service.util.BusRouteFinder;

/**
 * Bus route resource class
 * 
 * @author arun
 *
 */
@Path("/direct")
@Produces(MediaType.APPLICATION_JSON)
public class BusRouteResource {

	private static final Logger log = LoggerFactory.getLogger(BusRouteResource.class);

	// time out
	private final static int TIMEOUT = 5;

	/**
	 * Constructor
	 * 
	 */
	public BusRouteResource() {
		//
	}

	@GET
	public Response getDirectConnection(@QueryParam("dep_sid") final int dep_sid,
			@QueryParam("arr_sid") final int arr_sid) {

		BusRouteEntity entity = new BusRouteEntity(dep_sid, arr_sid);
		
		final ExecutorService executors = Executors.newSingleThreadExecutor();
		
		Future<Boolean> busRouteFinder = executors.submit(new BusRouteFinder(dep_sid, arr_sid));
		boolean isDirect = false;
		try {
			isDirect = busRouteFinder.get();
			executors.shutdown();
			if (!executors.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
				log.error("Timeout occured for stations:: DeartureStation: " + dep_sid + " and ArrivalStation: "
						+ arr_sid);
			}
		} catch (InterruptedException | ExecutionException e) {
			log.error("Interrupted error occured while finding direct bus route", e);
		}
		entity.setDirect_bus_route(isDirect);
		return Response.status(200).entity(entity).build();
	}

}
