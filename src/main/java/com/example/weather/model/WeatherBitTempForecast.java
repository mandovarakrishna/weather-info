package com.example.weather.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBitTempForecast {
	
	double temp;
	LocalDate valid_date;

	@JsonProperty("valid_date")
	public LocalDate getValid_date() {
		return this.valid_date;
	}

	public void setValid_date(LocalDate valid_date) {
		this.valid_date = valid_date;
	}

	@JsonProperty("temp")
	public double getTemp() {
		return this.temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}
}
