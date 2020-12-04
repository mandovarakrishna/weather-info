package com.example.weather.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Value;

import com.example.weather.openweathermap.model.OpenWeatherMap;
import com.example.weather.rest.RestClient;

@Named
public class OpenWeatherMapService {
	
	@Inject
	RestClient restClient;
	
	@Value("${OPEN_WEATHER_MAP}")
	String openWeatheMapUrl;
	
	public OpenWeatherMap getOpenWeatherMap(String city, String zipCode, String appKey) {
		OpenWeatherMap openWeatherMap = null;
		Response response = restClient.getCall(openWeatheMapUrl, city, zipCode, appKey);
		
		if(Status.OK.getStatusCode() == response.getStatus()) {
			openWeatherMap = response.readEntity(OpenWeatherMap.class);
		} else {
			
		}
		
		return openWeatherMap;
	}

}
