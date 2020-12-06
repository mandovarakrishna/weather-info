package com.example.weather.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.example.weather.model.CurrentWeatherInfo;
import com.example.weather.model.Forecast;
import com.example.weather.model.ForecastWeatherInfo;
import com.example.weather.model.OpenWeatherMapCurrent;
import com.example.weather.model.OpenWeatherMapForecast;
import com.example.weather.model.WeatherBitCurrent;
import com.example.weather.model.WeatherBitForecast;

@Named
public class WeatherHelperService {

	/** Averaging the Current Weather Information */
	public CurrentWeatherInfo averageOfCurrentWeather(OpenWeatherMapCurrent openWeatherMap,
			WeatherBitCurrent weatherBit) {
		CurrentWeatherInfo currentWeatherInfo = new CurrentWeatherInfo();

		currentWeatherInfo.setCity(openWeatherMap.getName());
		currentWeatherInfo.setWeather(weatherBit.getData().get(0).getWeather().getDescription());
		currentWeatherInfo.setAvgTemp(calculateAverage(convertToCelsius(openWeatherMap.getMain().getTemp()),
				weatherBit.getData().get(0).getTemp()));
		currentWeatherInfo.setAvgApparent(calculateAverage(convertToCelsius(openWeatherMap.getMain().getFeels_like()),
				weatherBit.getData().get(0).getApp_temp()));

		return currentWeatherInfo;
	}

	/** Averaging the Forecast Weather Information */
	public ForecastWeatherInfo averageOfForecastWeather(OpenWeatherMapForecast openWeatherMap,
			WeatherBitForecast weatherBit) {

		ForecastWeatherInfo forecastWeatherInfo = new ForecastWeatherInfo();
		forecastWeatherInfo.setCity(openWeatherMap.getCity().getName());

		List<Forecast> forecasts = new ArrayList<>();

		openWeatherMap.getList().stream().forEach(listWeather -> {
			if (isValidDate(listWeather.getDt_txt())) {
				weatherBit.getData().stream().forEach(tempForecast -> {
					if (isEqualDates(tempForecast.getValid_date(), listWeather.getDt_txt())) {
						Forecast forecast = new Forecast();
						forecast.setWeather(tempForecast.getWeatherBitDescription().getDescription());
						forecast.setDate(tempForecast.getValid_date());
						forecast.setAvgTemp(calculateAverage(convertToCelsius(listWeather.getMain().getTemp()),
								tempForecast.getTemp()));
						forecasts.add(forecast);
					}
				});
			}

		});

		forecastWeatherInfo.setData(forecasts);

		return forecastWeatherInfo;
	}

	/** HelperMethod to filter the validDate */
	protected static boolean isValidDate(LocalDateTime beginDateStr) {

		if (0 == beginDateStr.getHour()) {
			return true;
		}
		return false;

	}

	protected static boolean isEqualDates(LocalDate beginDateStr, LocalDateTime endDateStr) {

		LocalDate localDate = endDateStr.toLocalDate();

		if (beginDateStr.equals(localDate)) {
			return true;
		}
		return false;
	}

	/** HelperMethod for conversionToCelsius */
	protected double convertToCelsius(double value1) {

		return (value1 - 273.15);
	}

	/** HelperMethod for average calculation */
	protected String calculateAverage(double value1, double value2) {

		double finalValue = (value1 + value2) / 2;

		return String.format("%.2f", finalValue);
	}

}
