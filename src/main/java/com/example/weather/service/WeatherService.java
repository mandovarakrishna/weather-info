package com.example.weather.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.weather.error.ErrorService;
import com.example.weather.model.CurrentWeatherInfo;
import com.example.weather.model.ForecastWeatherInfo;
import com.example.weather.model.OpenWeatherMapCurrent;
import com.example.weather.model.OpenWeatherMapForecast;
import com.example.weather.model.WeatherBitCurrent;
import com.example.weather.model.WeatherBitForecast;

@Named
public class WeatherService {

	@Inject
	OpenWeatherMapService openWeatherMapService;

	@Inject
	WeatherBitService weatherBitService;

	@Inject
	WeatherHelperService weatherHelperService;

	@Inject
	ErrorService errorService;

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
	private static final String VALIDATION_FAILURE = "Validation Failure: ";
	private static final String ERROR_MESSAGE = "Error Message: ";

	public CurrentWeatherInfo getCurrentWeatherInformation(String city, String zipCode) {

		CurrentWeatherInfo currentWeatherInfo = null;
		OpenWeatherMapCurrent openWeatherMap = null;
		WeatherBitCurrent weatherBit = null;

		if (validateInputFields(city, zipCode)) {

			/** Call OpenWeatherMap API */
			CompletableFuture<OpenWeatherMapCurrent> openWeatherMapResponse = CompletableFuture.supplyAsync(() -> {

				try {

					return openWeatherMapService.getOpenWeatherMapCurrent(city, zipCode);
				} catch (Exception exception) {

					logger.error("OpenWeatherMap API",
							kv("Exception occured while calling OpenWeatherMap current information",
									Status.NOT_FOUND.toString()),
							kv(ERROR_MESSAGE, exception.getMessage()));
					errorService.addError("OpenWeatherMap API: " + exception.getMessage(), Status.NOT_FOUND.toString());
					return null;
				}

			});

			/** Call WeatherBit API */

			CompletableFuture<WeatherBitCurrent> weatherBitResponse = CompletableFuture.supplyAsync(() -> {

				try {

					return weatherBitService.getWeatherBitCurrent(city, zipCode);
				} catch (Exception exception) {

					logger.error("WeatherBit API", kv("Exception occured while calling WeatherBit current information",
							Status.NOT_FOUND.toString()), kv(ERROR_MESSAGE, exception.getMessage()));
					errorService.addError("WeatherBit API: " + exception.getMessage(), Status.NOT_FOUND.toString());
					return null;
				}

			});

			try {
				openWeatherMap = openWeatherMapResponse.get();
				weatherBit = weatherBitResponse.get();
			} catch (Exception exception) {

				logger.error("ExceutionException",
						kv("ExceutionException occured while completing future", Status.NOT_FOUND.toString()),
						kv(ERROR_MESSAGE, exception.getMessage()));
				errorService.addError("ExceutionException: " + exception.getMessage(), Status.NOT_FOUND.toString());
			}

			if (openWeatherMap != null && weatherBit != null && CollectionUtils.isEmpty(errorService.getErrors())) {
				currentWeatherInfo = weatherHelperService.averageOfCurrentWeather(openWeatherMap, weatherBit);
			}
		}

		return currentWeatherInfo;
	}

	public ForecastWeatherInfo getForecastWeatherInformation(String city, String zipCode) {

		ForecastWeatherInfo forecastWeatherInfo = null;
		OpenWeatherMapForecast openWeatherMap = null;
		WeatherBitForecast weatherBit = null;

		if (validateInputFields(city, zipCode)) {

			/** Call OpenWeatherMap API */

			CompletableFuture<OpenWeatherMapForecast> openWeatherMapResponse = CompletableFuture.supplyAsync(() -> {

				try {
					return openWeatherMapService.getOpenWeatherMapForecast(city, zipCode);
				} catch (Exception exception) {

					logger.error("OpenWeatherMap API",
							kv("Exception occured while calling OpenWeatherMap forecast information",
									Status.NOT_FOUND.toString()),
							kv(ERROR_MESSAGE, exception.getMessage()));
					errorService.addError("OpenWeatherMap API: " + exception.getMessage(), Status.NOT_FOUND.toString());
					return null;
				}

			});

			/** Call WeatherBit API */

			CompletableFuture<WeatherBitForecast> weatherBitResponse = CompletableFuture.supplyAsync(() -> {

				try {
					return weatherBitService.getWeatherBitForecast(city, zipCode);
				} catch (Exception exception) {

					logger.error("WeatherBit API", kv("Exception occured while calling WeatherBit forecast information",
							Status.NOT_FOUND.toString()), kv(ERROR_MESSAGE, exception.getMessage()));
					errorService.addError("WeatherBit API: " + exception.getMessage(), Status.NOT_FOUND.toString());
					return null;
				}

			});

			try {
				openWeatherMap = openWeatherMapResponse.get();
				weatherBit = weatherBitResponse.get();

			} catch (Exception exception) {

				logger.error("ExceutionException",
						kv("ExceutionException occured while completing future", Status.NOT_FOUND.toString()),
						kv(ERROR_MESSAGE, exception.getMessage()));
				errorService.addError("ExceutionException: " + exception.getMessage(), Status.NOT_FOUND.toString());
			}

			if (openWeatherMap != null && weatherBit != null && CollectionUtils.isEmpty(errorService.getErrors())) {
				forecastWeatherInfo = weatherHelperService.averageOfForecastWeather(openWeatherMap, weatherBit);
			}
		}

		return forecastWeatherInfo;
	}

	@SuppressWarnings("deprecation")
	protected boolean validateInputFields(String city, String zipCode) {
		boolean isSuccess = true;

		if (StringUtils.isEmpty(city) && StringUtils.isEmpty(zipCode)) {

			logger.info(VALIDATION_FAILURE, kv("City and ZipCode cannot be empty", Status.BAD_REQUEST.toString()));
			errorService.addError("City and ZipCode cannot be empty", Status.BAD_REQUEST.toString());
			isSuccess = false;
		} 
		else if (city!= null && !city.matches("[a-zA-Z]+")) {

			logger.info(VALIDATION_FAILURE, kv("Bad Request for city", Status.BAD_REQUEST.toString()));
			errorService.addError("Bad Request for city: " + city, Status.BAD_REQUEST.toString());
			isSuccess = false;
		}
		else if (zipCode!= null && !zipCode.matches("[0-9]+")) {

			logger.info(VALIDATION_FAILURE, kv("Bad Request for zipCode", Status.BAD_REQUEST.toString()));
			errorService.addError("Bad Request for zipCode: " + zipCode, Status.BAD_REQUEST.toString());
			isSuccess = false;
		}

		return isSuccess;
	}

}
