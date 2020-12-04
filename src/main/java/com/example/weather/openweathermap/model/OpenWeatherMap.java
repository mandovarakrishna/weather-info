package com.example.weather.openweathermap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMap {

	//Coord coord;

	//List<Weather> weather;

	//String base;

	//String visibility;

	//String dt;

	Main main;

	//Wind wind;

	//Clouds clouds;

	//Sys sys;

	//Root root;

	//double timezone;

	//int id;

	//String name;

	//int cod;

	/*
	 * public Coord getCoord() { return coord; }
	 * 
	 * public void setCoord(Coord coord) { this.coord = coord; }
	 * 
	 * public List<Weather> getWeather() { return weather; }
	 * 
	 * public void setWeather(List<Weather> weather) { this.weather = weather; }
	 * 
	 * public String getBase() { return base; }
	 * 
	 * public void setBase(String base) { this.base = base; }
	 * 
	 * public String getVisibility() { return visibility; }
	 * 
	 * public void setVisibility(String visibility) { this.visibility = visibility;
	 * }
	 * 
	 * public String getDt() { return dt; }
	 * 
	 * public void setDt(String dt) { this.dt = dt; }
	 */

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	/*
	 * public Wind getWind() { return wind; }
	 * 
	 * public void setWind(Wind wind) { this.wind = wind; }
	 * 
	 * public Clouds getClouds() { return clouds; }
	 * 
	 * public void setClouds(Clouds clouds) { this.clouds = clouds; }
	 * 
	 * public Sys getSys() { return sys; }
	 * 
	 * public void setSys(Sys sys) { this.sys = sys; }
	 * 
	 * public Root getRoot() { return root; }
	 * 
	 * public void setRoot(Root root) { this.root = root; }
	 * 
	 * public double getTimezone() { return timezone; }
	 * 
	 * public void setTimezone(double timezone) { this.timezone = timezone; }
	 * 
	 * public int getId() { return id; }
	 * 
	 * public void setId(int id) { this.id = id; }
	 * 
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 * 
	 * public int getCod() { return cod; }
	 * 
	 * public void setCod(int cod) { this.cod = cod; }
	 */
}
