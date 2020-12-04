package com.example.weather.openweathermap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coord {

	@JsonProperty("lon")
	public double getLon() {
		return this.lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	double lon;

	@JsonProperty("lat")
	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	double lat;
}
