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
	
	String valid_date;

	@JsonProperty("valid_date")
	public String getValid_date() {
		return valid_date;
	}

	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}
	
	
}
