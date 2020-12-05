package com.example.weather.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Value;

import com.example.weather.error.ErrorService;
import com.example.weather.error.WeatherBitErrorResponse;
import com.example.weather.model.WeatherBitCurrent;
import com.example.weather.model.WeatherBitForecast;
import com.example.weather.rest.RestClient;

@Named
public class WeatherBitService {

	@Inject
	RestClient restClient;

	@Inject
	ErrorService errorService;

	@Value("${WEATHER_BIT_CURRENT}")
	String weatherBitCurrentUrl;

	@Value("${WEATHER_BIT_FORECAST}")
	String weatherBitForecastUrl;

	public WeatherBitCurrent getWeatherBitCurrent(String city, String zipCode, String appKey) {
		WeatherBitCurrent weatherBit = null;

		Response response = restClient.getCall(addQueryParam(city, zipCode, appKey, weatherBitCurrentUrl));

		if (response != null && Status.OK.getStatusCode() == response.getStatus()) {
			weatherBit = response.readEntity(WeatherBitCurrent.class);
		} else {
			parseErrorResponse(response);
		}

		return weatherBit;
	}

	public WeatherBitForecast getWeatherBitForecast(String city, String zipCode, String appKey) {
		WeatherBitForecast weatherBit = null;
		Response

		response = restClient.getCall(addQueryParam(city, zipCode, appKey, weatherBitForecastUrl));

		if (response != null && Status.OK.getStatusCode() == response.getStatus()) {
			weatherBit = response.readEntity(WeatherBitForecast.class);
		} else {
			parseErrorResponse(response);
		}

		return weatherBit;
	}

	protected String addQueryParam(String city, String zipCode, String appKey, String url) {

		StringBuilder builder = new StringBuilder(url);
		if (city != null) {
			builder.append("?city=" + city);
		} else if (zipCode != null) {
			builder.append("?postal_code=" + zipCode);
		}
		builder.append("&key=" + appKey);

		return builder.toString();
	}

	protected void parseErrorResponse(Response response) {
		if (response != null && Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
			WeatherBitErrorResponse bitErrorResponse = response.readEntity(WeatherBitErrorResponse.class);
			errorService.addError(bitErrorResponse.getError(), "Error Occurred from WEATHER_BIT_API");
		} else {
			errorService.addError("Error Occurred while connecting to WEATHER_BIT", Status.NOT_FOUND.toString());
		}
	}

}
