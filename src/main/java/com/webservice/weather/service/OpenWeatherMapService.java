package com.webservice.weather.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.Base64;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.webservice.weather.error.ErrorService;
import com.webservice.weather.error.OpenWeatherErrorResponse;
import com.webservice.weather.model.OpenWeatherMapCurrent;
import com.webservice.weather.model.OpenWeatherMapForecast;
import com.webservice.weather.rest.RestClient;

@Named
public class OpenWeatherMapService {

	@Inject
	RestClient restClient;

	@Inject
	ErrorService errorService;

	@Value("${OPEN_WEATHER_MAP_CURRENT}")
	String openWeatherMapCurrentUrl;

	@Value("${OPEN_WEATHER_MAP_FORECAST}")
	String openWeatherMapForecastUrl;
	
	@Value("${OPEN_WEATHER_MAP_VALUE}")
	String openWeatherMapValue;

	private static final Logger logger = LoggerFactory.getLogger(OpenWeatherMapService.class);

	/** GET OpenWeatherMap Current Information */
	public OpenWeatherMapCurrent getOpenWeatherMapCurrent(String city, String zipCode) {
		OpenWeatherMapCurrent openWeatherMap = null;

		Response response = restClient.getCall(addQueryParam(city, zipCode, getAppKey(), openWeatherMapCurrentUrl));

		if (response != null && Status.OK.getStatusCode() == response.getStatus()) {
			openWeatherMap = response.readEntity(OpenWeatherMapCurrent.class);
		} else {
			if (CollectionUtils.isEmpty(errorService.getErrors())) {
				parseErrorResponse(response);
			}
		}

		return openWeatherMap;
	}

	/** GET OpenWeatherMap Forecast Information */
	public OpenWeatherMapForecast getOpenWeatherMapForecast(String city, String zipCode) {
		OpenWeatherMapForecast openWeatherMap = null;

		Response response = restClient.getCall(addQueryParam(city, zipCode, getAppKey(), openWeatherMapForecastUrl));

		if (response != null && Status.OK.getStatusCode() == response.getStatus()) {
			openWeatherMap = response.readEntity(OpenWeatherMapForecast.class);
		} else {
			if (CollectionUtils.isEmpty(errorService.getErrors())) {
				parseErrorResponse(response);
			}
		}

		return openWeatherMap;
	}

	/** Add QueryParam to the URL */
	protected String addQueryParam(String city, String zipCode, String appKey, String url) {

		StringBuilder builder = new StringBuilder(url);
		if (city != null) {
			builder.append("?q=" + city);
		} else if (zipCode != null) {
			builder.append("?zip=" + zipCode);
		}
		builder.append("&appid=" + appKey);

		return builder.toString();
	}

	protected String getAppKey() {
		return new String(Base64.getDecoder().decode(openWeatherMapValue));
	}

	protected void parseErrorResponse(Response response) {
		if (response != null) {

			OpenWeatherErrorResponse error = response.readEntity(OpenWeatherErrorResponse.class);
			logger.error("Error occured at OpenWeatherMap API: ", kv(error.getMessage(), error.getCod()));
			errorService.addError(error.getMessage(), error.getCod());
		} else {

			errorService.addError("Error Occurred while connecting to OPEN_WEATHER_MAP", Status.NOT_FOUND.toString());
		}
	}

}
