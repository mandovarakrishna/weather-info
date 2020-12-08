package com.example.weather.resource;

import static net.logstash.logback.argument.StructuredArguments.kv;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.example.weather.error.ErrorService;
import com.example.weather.model.CurrentWeatherInfo;
import com.example.weather.model.ForecastWeatherInfo;
import com.example.weather.service.WeatherService;

@Path("/")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@RolesAllowed("INTERNAL_USER")
public class WeatherResource {

	@Inject
	WeatherService weatherService;

	@Inject
	ErrorService errorService;

	private static final Logger logger = LoggerFactory.getLogger(WeatherResource.class);

	@GET
	@Path("current-weather")
	public Response getCurrentWeatherInfo(@QueryParam("city") @Pattern(regexp = "[a-zA-Z]+") String city,
			@QueryParam("zipCode") @Pattern(regexp = "[0-9]+") String zipCode) {

		logger.info("Begin Request", kv("logIncomingRequest", "current-weather"), kv("city", city),
				kv("zipCode", zipCode));

		errorService.intializeErrors();

		CurrentWeatherInfo currentWeatherInfo = weatherService.getCurrentWeatherInformation(city, zipCode);

		if (CollectionUtils.isEmpty(errorService.getErrors())) {
			logger.info("Current weather info call is successful");
			return Response.status(Status.OK).entity(currentWeatherInfo).build();
		}

		logger.info("Current weather info call failed");
		return errorResponse(Status.BAD_REQUEST);
	}

	@GET
	@Path("forecast-weather")
	public Response getForecastWeatherInfo(@QueryParam("city") @Pattern(regexp = "[a-zA-Z]+") String city,
			@QueryParam("zipCode") @Pattern(regexp = "[0-9]+") String zipCode) {

		logger.info("Begin Request", kv("logIncomingRequest", "forecast-weather"), kv("city", city),
				kv("zipCode", zipCode));

		errorService.intializeErrors();

		ForecastWeatherInfo forecastWeatherInfo = weatherService.getForecastWeatherInformation(city, zipCode);

		if (CollectionUtils.isEmpty(errorService.getErrors())) {
			logger.info("Forecast weather info call is successful");
			return Response.status(Status.OK).entity(forecastWeatherInfo).build();
		}

		logger.info("Forecast weather info call failed");
		return errorResponse(Status.BAD_REQUEST);
	}

	private Response errorResponse(StatusType statusType) {
		return Response.status(statusType).entity(errorService.getErrors()).build();
	}

}
