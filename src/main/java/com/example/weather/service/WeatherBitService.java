package com.example.weather.service;

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
	
	@Value("${WEATHER_BIT_VALUE}")
	String weatherBitValue;

	private static final Logger logger = LoggerFactory.getLogger(WeatherBitService.class);

	/** GET WeatherBit Current Information */
	public WeatherBitCurrent getWeatherBitCurrent(String city, String zipCode) {
		WeatherBitCurrent weatherBit = null;

		Response response = restClient.getCall(addQueryParam(city, zipCode, getAppKey(), weatherBitCurrentUrl));

		if (response != null && Status.OK.getStatusCode() == response.getStatus()) {
			weatherBit = response.readEntity(WeatherBitCurrent.class);
		} else {
			if (CollectionUtils.isEmpty(errorService.getErrors())) {
				parseErrorResponse(response);
			}
		}

		return weatherBit;
	}

	/** GET WeatherBit Forecast Information */
	public WeatherBitForecast getWeatherBitForecast(String city, String zipCode) {
		WeatherBitForecast weatherBit = null;
		
		Response response = restClient.getCall(addQueryParam(city, zipCode, getAppKey(), weatherBitForecastUrl));

		if (response != null && Status.OK.getStatusCode() == response.getStatus()) {
			weatherBit = response.readEntity(WeatherBitForecast.class);
		} else {
			if (CollectionUtils.isEmpty(errorService.getErrors())) {
				parseErrorResponse(response);
			}
		}

		return weatherBit;
	}

	/** Add QueryParam to the URL */
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
	
	protected String getAppKey() {
		return new String(Base64.getDecoder().decode(weatherBitValue));
	}

	protected void parseErrorResponse(Response response) {

		if (response != null && Status.NO_CONTENT.getStatusCode() != response.getStatus()) {

			WeatherBitErrorResponse bitErrorResponse = response.readEntity(WeatherBitErrorResponse.class);
			logger.error("Error occured at OpenWeatherMap API: ",
					kv(bitErrorResponse.getError(), "Error Occurred from WEATHER_BIT_API"));
			errorService.addError(bitErrorResponse.getError(), "Error Occurred from WEATHER_BIT_API");
		} else {
			errorService.addError("Error Occurred while connecting to WEATHER_BIT", Status.NOT_FOUND.toString());
		}
	}

}
