package com.example.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBitCurrent {

	@JsonProperty("data")
	public List<Datum> getData() {
		return this.data;
	}

	public void setData(List<Datum> data) {
		this.data = data;
	}

	List<Datum> data;

}
