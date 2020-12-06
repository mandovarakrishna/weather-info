package com.example.weather.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.example.weather.resource.WeatherResource;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {

		register(WeatherResource.class);
		register(CorsFilter.class);
	}
}
