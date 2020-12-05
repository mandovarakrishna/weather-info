package com.example.weather.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Value;

import com.example.weather.error.ErrorService;
import com.example.weather.error.OpenWeatherErrorResponse;
import com.example.weather.model.OpenWeatherMap;
import com.example.weather.rest.RestClient;

@Named
public class OpenWeatherMapService {

	@Inject
	RestClient restClient;

	@Inject
	ErrorService errorService;

	@Value("${OPEN_WEATHER_MAP}")
	String openWeatheMapUrl;

	public OpenWeatherMap getOpenWeatherMap(String city, String zipCode, String appKey) {
		OpenWeatherMap openWeatherMap = null;

		Response response = restClient.getCall(addQueryParam(city, zipCode, appKey));

		if (response != null && Status.OK.getStatusCode() == response.getStatus()) {
			openWeatherMap = response.readEntity(OpenWeatherMap.class);
		} else {
			parseErrorResponse(response);
		}

		return openWeatherMap;
	}

	protected String addQueryParam(String city, String zipCode, String appKey) {

		StringBuilder builder = new StringBuilder(openWeatheMapUrl);
		if (city != null) {
			builder.append("?q=" + city);
		} else if (zipCode != null) {
			builder.append("?zip=" + zipCode);
		}
		builder.append("&appid=" + appKey);

		return builder.toString();
	}

	protected void parseErrorResponse(Response response) {
		if (response != null) {
			OpenWeatherErrorResponse error = response.readEntity(OpenWeatherErrorResponse.class);
			errorService.addError(error.getMessage(), error.getCod());
		} else {
			errorService.addError("Error Occurred while connecting to OPEN_WEATHER_MAP", Status.NOT_FOUND.toString());
		}
	}

}
