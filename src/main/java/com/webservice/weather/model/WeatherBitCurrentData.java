package com.webservice.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBitCurrentData {

	WeatherBitDescription weather;
	String city_name;
	double temp;
	double app_temp;
	String valid_date;

	@JsonProperty("weather")
	public WeatherBitDescription getWeather() {
		return this.weather;
	}

	public void setWeather(WeatherBitDescription weather) {
		this.weather = weather;
	}

	@JsonProperty("city_name")
	public String getCity_name() {
		return this.city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	@JsonProperty("temp")
	public double getTemp() {
		return this.temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	@JsonProperty("app_temp")
	public double getApp_temp() {
		return this.app_temp;
	}

	public void setApp_temp(double app_temp) {
		this.app_temp = app_temp;
	}

	@JsonProperty("valid_date")
	public String getValid_date() {
		return valid_date;
	}

	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}

}
