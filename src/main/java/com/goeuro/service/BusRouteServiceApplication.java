package com.goeuro.service;

import com.goeuro.core.BusRouteDataContainer;
import com.goeuro.service.inner.BusRouteServiceConfiguration;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class BusRouteServiceApplication extends Application<BusRouteServiceConfiguration> {

	public static void main(String[] args) throws Exception {
		BusRouteDataContainer.getInstance().createDataStructureFromFile(args[2]);
		String[] argsToSend = new String[] { args[0], args[1] };
		new BusRouteServiceApplication().run(argsToSend);
	}

	@Override
	public void run(BusRouteServiceConfiguration arg0, Environment arg1) throws Exception {
		final BusRouteResource resource = new BusRouteResource();
		arg1.jersey().register(resource);
	}

}
