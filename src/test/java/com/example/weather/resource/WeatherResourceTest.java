package com.example.weather.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.weather.error.ErrorService;
import com.example.weather.error.WeatherError;
import com.example.weather.model.CurrentWeatherInfo;
import com.example.weather.model.ForecastWeatherInfo;
import com.example.weather.service.WeatherService;

@ExtendWith(MockitoExtension.class)
public class WeatherResourceTest {

	@InjectMocks
	WeatherResource weatherResource;

	@Mock
	WeatherService weatherService;

	@Mock
	ErrorService errorService;

	@Test
	public void testGetCurrentWeatherInfo() {

		CurrentWeatherInfo currentWeatherInfo = new CurrentWeatherInfo();
		Mockito.when(weatherService.getCurrentWeatherInformation(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(currentWeatherInfo);
		Response response = weatherResource.getCurrentWeatherInfo("Test", "1005");
		assertEquals(Status.OK, response.getStatusInfo());
	}

	@Test
	public void testGetCurrentWeatherInfoBadRequest() {

		List<WeatherError> errors = new ArrayList<>();
		WeatherError error = new WeatherError("Bad Data", "Bad Request");
		errors.add(error);
		CurrentWeatherInfo currentWeatherInfo = new CurrentWeatherInfo();
		Mockito.when(weatherService.getCurrentWeatherInformation(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(currentWeatherInfo);
		Mockito.when(errorService.getErrors()).thenReturn(errors);
		Response response = weatherResource.getCurrentWeatherInfo("Test", "1005");
		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void testGetForecastWeatherInfo() {

		ForecastWeatherInfo currentWeatherInfo = new ForecastWeatherInfo();
		Mockito.when(weatherService.getForecastWeatherInformation(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(currentWeatherInfo);
		Response response = weatherResource.getForecastWeatherInfo("Test", "1005");
		assertEquals(Status.OK, response.getStatusInfo());
	}

	@Test
	public void testGetForecastWeatherInfoBadRequest() {

		List<WeatherError> errors = new ArrayList<>();
		WeatherError error = new WeatherError("Bad Data", "Bad Request");
		errors.add(error);
		ForecastWeatherInfo currentWeatherInfo = new ForecastWeatherInfo();
		Mockito.when(weatherService.getForecastWeatherInformation(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(currentWeatherInfo);
		Mockito.when(errorService.getErrors()).thenReturn(errors);
		Response response = weatherResource.getForecastWeatherInfo("Test", "1005");
		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}
}
