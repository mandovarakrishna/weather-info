package com.example.weather.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListWeather {

	Main main;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime dt_txt;

	@JsonProperty("main")
	public Main getMain() {
		return this.main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	@JsonProperty("dt_txt")
	public LocalDateTime getDt_txt() {
		return this.dt_txt;
	}

	public void setDt_txt(LocalDateTime dt_txt) {
		this.dt_txt = dt_txt;
	}
}
