package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapCurrent {

	OpenWeatherMapData main;
	String name;

	@JsonProperty("main")
	public OpenWeatherMapData getMain() {
		return main;
	}

	public void setMain(OpenWeatherMapData main) {
		this.main = main;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
