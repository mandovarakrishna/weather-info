package com.example.weather.model;

import java.time.LocalDate;

public class CurrentWeatherInfo {

	String avgTemp;
	String avgApparent;
	String city;
	String weather;
	LocalDate date;

	public String getAvgTemp() {
		return avgTemp;
	}

	public void setAvgTemp(String avgTemp) {
		this.avgTemp = avgTemp;
	}

	public String getAvgApparent() {
		return avgApparent;
	}

	public void setAvgApparent(String avgApparent) {
		this.avgApparent = avgApparent;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
