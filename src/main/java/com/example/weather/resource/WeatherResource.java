package com.example.weather.resource;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.springframework.util.CollectionUtils;

import com.example.weather.error.ErrorService;
import com.example.weather.model.OpenWeatherMap;
import com.example.weather.service.WeatherService;

@Path("/")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WeatherResource {
	
	@Inject
	WeatherService weatherService;
	
	@Inject
	ErrorService errorService;
	
	@GET
	@Path("current-weather")
	public Response getWeatherInfo(@QueryParam("city") String city, 
			@QueryParam("zipCode") String zipCode,
			@HeaderParam("appid") @NotNull String appKey) {
		
		errorService.intializeErrors();
		
		OpenWeatherMap openWeatherMap  = weatherService.getWeatherInformation(city, zipCode, appKey);
		
		if(CollectionUtils.isEmpty(errorService.getErrors())) {
			return Response.status(Status.OK).entity(openWeatherMap).build();
		}
		
		return errorResponse(Status.BAD_REQUEST);
	}
	
	private Response errorResponse(StatusType statusType) {
		return Response.status(statusType).entity(errorService.getErrors()).build();
	}
	
}
