package com.webservice.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBitCurrent {

	List<WeatherBitCurrentData> data;

	@JsonProperty("data")
	public List<WeatherBitCurrentData> getData() {
		return this.data;
	}

	public void setData(List<WeatherBitCurrentData> data) {
		this.data = data;
	}
}
