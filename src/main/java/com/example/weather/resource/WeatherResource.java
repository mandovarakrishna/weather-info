package com.example.weather.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.weather.openweathermap.model.OpenWeatherMap;
import com.example.weather.service.WeatherService;

@Path("/")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WeatherResource {
	
	@Inject
	WeatherService weatherService;
	
	@GET
	@Path("weather-info")
	public Response getWeatherInfo(@QueryParam("city") String city, 
			@QueryParam("zipCode") String zipCode) {
		
		OpenWeatherMap openWeatherMap  = weatherService.getWeatherInformation(city, zipCode);
		
		if(openWeatherMap != null) {
			return Response.status(Status.OK).entity(openWeatherMap).build();
		}
		
		return Response.status(Status.BAD_REQUEST).entity("Good Request").build();
	}
	
}
