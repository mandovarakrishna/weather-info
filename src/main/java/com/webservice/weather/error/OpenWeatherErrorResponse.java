package com.webservice.weather.error;

public class OpenWeatherErrorResponse {
	
	private String cod;
	private String message;
	
	public OpenWeatherErrorResponse(String cod, String message) {
		super();
		this.cod = cod;
		this.message = message;
	}
	
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
