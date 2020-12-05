package com.example.weather.model;

import java.util.List;

public class ForecastWeatherInfo {

	List<Forecast> data;
	String city;

	public List<Forecast> getData() {
		return data;
	}

	public void setData(List<Forecast> data) {
		this.data = data;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
