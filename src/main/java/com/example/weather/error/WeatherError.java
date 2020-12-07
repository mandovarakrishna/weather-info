package com.example.weather.error;

public class WeatherError {

	String message;
	String type;
	
	public WeatherError() {}

	public WeatherError(String message, String type) {
		super();
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
