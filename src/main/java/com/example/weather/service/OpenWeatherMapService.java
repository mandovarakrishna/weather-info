package com.example.weather.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.weather.openweathermap.model.OpenWeatherMap;
import com.example.weather.rest.RestClient;

@Named
public class OpenWeatherMapService {
	
	@Inject
	RestClient restClient;
	
	public OpenWeatherMap getOpenWeatherMap(String city, String zipCode) {
		OpenWeatherMap openWeatherMap = null;
		Response response = restClient.getCall("https://api.openweathermap.org/data/2.5/weather?q=London&APPID=6c7da970e8c0052dfc8d7bc4385cf4a9");
		
		if(Status.OK.getStatusCode() == response.getStatus()) {
			openWeatherMap = response.readEntity(OpenWeatherMap.class);
		}
		
		return openWeatherMap;
	}

}
