package com.example.weather.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherBitErrorResponse {

	String error;

	public WeatherBitErrorResponse(String error) {
		super();
		this.error = error;
	}

	@JsonProperty("error")
	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
