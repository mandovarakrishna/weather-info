package com.example.weather.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends WebApplicationException{
	
	/**
     * Create a HTTP 401 (Unauthorized) exception.
    */
    public UnauthorizedException() {
        super(Response.status(Status.UNAUTHORIZED).build());
    }

    /**
     * Create a HTTP 401 (Unauthorized) exception.
    */
    public UnauthorizedException(WeatherError weatherError) {
        super(Response.status(Status.UNAUTHORIZED).entity(weatherError).build());
    }
}
