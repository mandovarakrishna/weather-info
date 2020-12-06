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

	public CurrentWeatherInfo getCurrentWeatherInformation(String city, String zipCode, String appKey, String key) {

		CurrentWeatherInfo currentWeatherInfo = null;
		OpenWeatherMapCurrent openWeatherMap = null;
		WeatherBitCurrent weatherBit = null;

		if (validateInputFields(city, zipCode, appKey, key)) {

			/** Call OpenWeatherMap API */
			CompletableFuture<OpenWeatherMapCurrent> openWeatherMapResponse = CompletableFuture.supplyAsync(() -> {

				try {

					return openWeatherMapService.getOpenWeatherMapCurrent(city, zipCode, appKey);
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

					return weatherBitService.getWeatherBitCurrent(city, zipCode, key);
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

	public ForecastWeatherInfo getForecastWeatherInformation(String city, String zipCode, String appKey, String key) {

		ForecastWeatherInfo forecastWeatherInfo = null;
		OpenWeatherMapForecast openWeatherMap = null;
		WeatherBitForecast weatherBit = null;

		if (validateInputFields(city, zipCode, appKey, key)) {

			/** Call OpenWeatherMap API */

			CompletableFuture<OpenWeatherMapForecast> openWeatherMapResponse = CompletableFuture.supplyAsync(() -> {

				try {
					return openWeatherMapService.getOpenWeatherMapForecast(city, zipCode, appKey);
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
					return weatherBitService.getWeatherBitForecast(city, zipCode, key);
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
	protected boolean validateInputFields(String city, String zipCode, String appKey, String key) {
		boolean isSuccess = true;

		if (StringUtils.isEmpty(city) && StringUtils.isEmpty(zipCode)) {

			logger.info(VALIDATION_FAILURE, kv("City and ZipCode cannot be empty", Status.BAD_REQUEST.toString()));
			errorService.addError("City and ZipCode cannot be empty", Status.BAD_REQUEST.toString());
			isSuccess = false;
		} else if (StringUtils.isEmpty(appKey)) {

			logger.info(VALIDATION_FAILURE,
					kv("appKey for OpenWeatherMap cannot be empty", Status.BAD_REQUEST.toString()));
			errorService.addError("appKey for OpenWeatherMap cannot be empty", Status.BAD_REQUEST.toString());
			isSuccess = false;
		} else if (StringUtils.isEmpty(key)) {

			logger.info(VALIDATION_FAILURE, kv("appKey for WeatherBit cannot be empty", Status.BAD_REQUEST.toString()));
			errorService.addError("appKey for WeatherBit cannot be empty", Status.BAD_REQUEST.toString());
			isSuccess = false;
		}

		return isSuccess;
	}

}
