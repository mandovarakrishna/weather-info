package com.webservice.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBitForecast {

	List<WeatherBitTempForecast> data;
	String city_name;

	@JsonProperty("data")
	public List<WeatherBitTempForecast> getData() {
		return this.data;
	}

	public void setData(List<WeatherBitTempForecast> data) {
		this.data = data;
	}

	@JsonProperty("city_name")
	public String getCity_name() {
		return this.city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

}
