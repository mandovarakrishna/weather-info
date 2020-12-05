package com.example.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapForecast {

	@JsonProperty("list")
	public List<ListWeather> getList() {
		return this.list;
	}

	public void setList(List<ListWeather> list) {
		this.list = list;
	}

	List<ListWeather> list;

	@JsonProperty("city")
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	City city;
}
