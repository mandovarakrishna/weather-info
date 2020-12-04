package com.example.weather.service;

import javax.inject.Inject;
import javax.inject.Named;

import com.example.weather.openweathermap.model.OpenWeatherMap;

@Named
public class WeatherService {
	
	@Inject
	OpenWeatherMapService openWeatherMapService;
	
	public OpenWeatherMap getWeatherInformation(String city, String zipCode) {
		
		//Call OpenWeatherMap API 
		OpenWeatherMap openWeatherMap =openWeatherMapService.getOpenWeatherMap(city, zipCode);
		
		return openWeatherMap;
	}

}
