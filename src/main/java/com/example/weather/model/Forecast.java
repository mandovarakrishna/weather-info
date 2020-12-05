package com.example.weather.model;

import java.time.LocalDate;

public class Forecast {

	LocalDate date;
	String avgTemp;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getAvgTemp() {
		return avgTemp;
	}

	public void setAvgTemp(String avgTemp) {
		this.avgTemp = avgTemp;
	}
}
