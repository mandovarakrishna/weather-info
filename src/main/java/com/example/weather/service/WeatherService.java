package com.example.weather.service;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.weather.error.ErrorService;
import com.example.weather.model.CurrentWeatherInfo;
import com.example.weather.model.OpenWeatherMap;
import com.example.weather.model.WeatherBit;

@Named
public class WeatherService {

	@Inject
	OpenWeatherMapService openWeatherMapService;

	@Inject
	WeatherBitService weatherBitService;

	@Inject
	ErrorService errorService;

	public CurrentWeatherInfo getCurrentWeatherInformation(String city, String zipCode, String appKey, String key) {

		CurrentWeatherInfo currentWeatherInfo = null;
		OpenWeatherMap openWeatherMap = null;
		WeatherBit weatherBit = null;

		if (validateInputFields(city, zipCode, appKey, key)) {

			/** Call OpenWeatherMap API */
			CompletableFuture<OpenWeatherMap> openWeatherMapResponse = CompletableFuture.supplyAsync(() -> {

				try {
					
					return openWeatherMapService.getOpenWeatherMap(city, zipCode, appKey);
				} catch (Exception e) {
					errorService.addError("Exception occured while calling OpenWeatherMap", Status.NOT_FOUND.toString());
					return null;
				}

			});

			/** Call WeatherBit API */

			CompletableFuture<WeatherBit> weatherBitResponse = CompletableFuture.supplyAsync(() -> {

				try {
					
					return weatherBitService.getWeatherBit(city, zipCode, key);
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
				currentWeatherInfo = averageOfCurrentWeather(openWeatherMap, weatherBit);
			} else {
				
			}
		}

		return currentWeatherInfo;
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

	protected CurrentWeatherInfo averageOfCurrentWeather(OpenWeatherMap openWeatherMap, WeatherBit weatherBit) {
		CurrentWeatherInfo currentWeatherInfo = new CurrentWeatherInfo();

		currentWeatherInfo.setCity(openWeatherMap.getName());
		currentWeatherInfo.setAvgTemp(calculateAverage(convertToCelsius(openWeatherMap.getMain().getTemp()),
				weatherBit.getData().get(0).getTemp()));
		currentWeatherInfo.setAvgApparent(calculateAverage(convertToCelsius(openWeatherMap.getMain().getFeels_like()),
				weatherBit.getData().get(0).getApp_temp()));

		return currentWeatherInfo;
	}

	protected double convertToCelsius(double value1) {

		return (value1 - 273.15);
	}

	protected String calculateAverage(double value1, double value2) {

		double finalValue = (value1 + value2) / 2;

		return String.format("%.2f", finalValue);
	}

}
