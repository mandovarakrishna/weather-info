package com.webservice.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapForecast {

	List<ListWeather> list;
	City city;

	@JsonProperty("list")
	public List<ListWeather> getList() {
		return this.list;
	}

	public void setList(List<ListWeather> list) {
		this.list = list;
	}

	@JsonProperty("city")
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
