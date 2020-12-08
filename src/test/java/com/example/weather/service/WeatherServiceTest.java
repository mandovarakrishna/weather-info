package com.example.weather.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

	@InjectMocks
	WeatherService weatherService;

	@Mock
	OpenWeatherMapService openWeatherMapService;

	@Mock
	WeatherBitService weatherBitService;

	@Test
	public void testGetCurrentWeatherInformations() {

		assertNull(weatherService.getCurrentWeatherInformation("TEST", "1000"));
	}

	@Test
	public void testGetForecastWeatherInformations() {

		assertNull(weatherService.getForecastWeatherInformation("TEST", "1000"));
	}
}
