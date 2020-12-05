package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Datum {

	@JsonProperty("city_name")
	public String getCity_name() {
		return this.city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	String city_name;

	@JsonProperty("temp")
	public double getTemp() {
		return this.temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	double temp;

	@JsonProperty("app_temp")
	public double getApp_temp() {
		return this.app_temp;
	}

	public void setApp_temp(double app_temp) {
		this.app_temp = app_temp;
	}

	double app_temp;
}
