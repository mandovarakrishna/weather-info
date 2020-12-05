package com.example.weather.service;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

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

	public CurrentWeatherInfo getCurrentWeatherInformation(String city, String zipCode, String appKey, String key) {

		CurrentWeatherInfo currentWeatherInfo = null;
		OpenWeatherMapCurrent openWeatherMap = null;
		WeatherBitCurrent weatherBit = null;

		if (validateInputFields(city, zipCode, appKey, key)) {

			/** Call OpenWeatherMap API */
			CompletableFuture<OpenWeatherMapCurrent> openWeatherMapResponse = CompletableFuture.supplyAsync(() -> {

				try {

					return openWeatherMapService.getOpenWeatherMapCurrent(city, zipCode, appKey);
				} catch (Exception e) {
					errorService.addError("Exception occured while calling OpenWeatherMap",
							Status.NOT_FOUND.toString());
					return null;
				}

			});

			/** Call WeatherBit API */

			CompletableFuture<WeatherBitCurrent> weatherBitResponse = CompletableFuture.supplyAsync(() -> {

				try {

					return weatherBitService.getWeatherBitCurrent(city, zipCode, key);
				} catch (Exception e) {
					errorService.addError("Exception occured while calling WeatherBit", Status.NOT_FOUND.toString());
					return null;
				}

			});

			try {
				openWeatherMap = openWeatherMapResponse.get();
				weatherBit = weatherBitResponse.get();
			} catch (Exception e) {
				errorService.addError("Exception occured while calling WeatherBit", Status.NOT_FOUND.toString());
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
				} catch (Exception e) {
					errorService.addError("Exception occured while calling OpenWeatherMap",
							Status.NOT_FOUND.toString());
					return null;
				}

			});

			/** Call WeatherBit API */

			CompletableFuture<WeatherBitForecast> weatherBitResponse = CompletableFuture.supplyAsync(() -> {

				try {
					return weatherBitService.getWeatherBitForecast(city, zipCode, key);
				} catch (Exception e) {
					errorService.addError("Exception occured while calling WeatherBit", Status.NOT_FOUND.toString());
					return null;
				}

			});

			try {
				openWeatherMap = openWeatherMapResponse.get();
				weatherBit = weatherBitResponse.get();
			} catch (Exception e) {
				errorService.addError("Exception occured while calling WeatherBit", Status.NOT_FOUND.toString());
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
			errorService.addError("City and ZipCode cannot be empty", Status.BAD_REQUEST.toString());
			isSuccess = false;
		} else if (StringUtils.isEmpty(appKey)) {
			errorService.addError("appKey for OpenWeatherMap cannot be empty", Status.BAD_REQUEST.toString());
			isSuccess = false;
		} else if (StringUtils.isEmpty(key)) {
			errorService.addError("appKey for WeatherBit cannot be empty", Status.BAD_REQUEST.toString());
			isSuccess = false;
		}

		return isSuccess;
	}

}
