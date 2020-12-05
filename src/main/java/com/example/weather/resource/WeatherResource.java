package com.example.weather.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import com.example.weather.error.ErrorService;
import com.example.weather.model.CurrentWeatherInfo;
import com.example.weather.model.ForecastWeatherInfo;
import com.example.weather.service.WeatherService;

@RestController
@Path("/")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class WeatherResource {

	@Autowired
	WeatherService weatherService;

	@Inject
	ErrorService errorService;

	@GET
	@Path("current-weather")
	public Response getCurrentWeatherInfo(@QueryParam("city") String city, @QueryParam("zipCode") String zipCode,
			@HeaderParam("appid") String appKey, @HeaderParam("key") String key) {

		errorService.intializeErrors();

		CurrentWeatherInfo currentWeatherInfo = weatherService.getCurrentWeatherInformation(city, zipCode, appKey, key);

		if (CollectionUtils.isEmpty(errorService.getErrors())) {
			return Response.status(Status.OK).entity(currentWeatherInfo).build();
		}

		return errorResponse(Status.BAD_REQUEST);
	}

	@GET
	@Path("forecast-weather")
	public Response getForecastWeatherInfo(@QueryParam("city") String city, @QueryParam("zipCode") String zipCode,
			@HeaderParam("appid") String appKey, @HeaderParam("key") String key) {

		errorService.intializeErrors();

		ForecastWeatherInfo forecastWeatherInfo = weatherService.getForecastWeatherInformation(city, zipCode, appKey,
				key);

		if (CollectionUtils.isEmpty(errorService.getErrors())) {
			return Response.status(Status.OK).entity(forecastWeatherInfo).build();
		}

		return errorResponse(Status.BAD_REQUEST);
	}

	private Response errorResponse(StatusType statusType) {
		return Response.status(statusType).entity(errorService.getErrors()).build();
	}

}
