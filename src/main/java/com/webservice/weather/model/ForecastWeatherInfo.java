package com.webservice.weather.model;

import java.util.List;

public class ForecastWeatherInfo {

	List<Forecast> forecast;
	String city;

	public List<Forecast> getForecast() {
		return forecast;
	}

	public void setForecast(List<Forecast> forecast) {
		this.forecast = forecast;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
