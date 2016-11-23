package com.goeuro.service.inner;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class BusRouteServiceConfiguration extends Configuration {
	   @NotEmpty
	    private String version;
	 
	    @JsonProperty
	    public String getVersion() {
	        return version;
	    }
	 
	    @JsonProperty
	    public void setVersion(String version) {
	        this.version = version;
	    }
}
