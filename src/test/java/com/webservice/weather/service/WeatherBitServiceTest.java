package com.webservice.weather.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.webservice.weather.error.ErrorService;
import com.webservice.weather.error.WeatherBitErrorResponse;
import com.webservice.weather.model.WeatherBitCurrent;
import com.webservice.weather.model.WeatherBitForecast;
import com.webservice.weather.rest.RestClient;
import com.webservice.weather.service.WeatherBitService;

@ExtendWith(MockitoExtension.class)
public class WeatherBitServiceTest {

	@InjectMocks
	WeatherBitService weatherBitService;

	@Mock
	RestClient restClient;

	@Mock
	ErrorService errorService;

	@Mock
	Response response;

	@BeforeEach
	public void setUp() {

		ReflectionTestUtils.setField(weatherBitService, "weatherBitCurrentUrl", "http://test");
		ReflectionTestUtils.setField(weatherBitService, "weatherBitForecastUrl", "http://test");
		ReflectionTestUtils.setField(weatherBitService, "weatherBitValue", "test");
	}

	@Test
	public void testGetWeatherBitCurrent() {

		Mockito.when(restClient.getCall(Mockito.anyString())).thenReturn(response);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(WeatherBitCurrent.class)).thenReturn(new WeatherBitCurrent());
		assertNotNull(weatherBitService.getWeatherBitCurrent("TEST", "1000"));
	}

	@Test
	public void testGetWeatherBitBadRequest() {

		Mockito.when(restClient.getCall(Mockito.anyString())).thenReturn(response);
		Mockito.when(response.getStatus()).thenReturn(400);
		Mockito.when(response.readEntity(WeatherBitErrorResponse.class)).thenReturn(new WeatherBitErrorResponse(""));
		assertNull(weatherBitService.getWeatherBitCurrent("TEST", "1000"));
		Mockito.verify(errorService, Mockito.times(1)).getErrors();
	}

	@Test
	public void testGetWeatherBitForecast() {

		Mockito.when(restClient.getCall(Mockito.anyString())).thenReturn(response);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(WeatherBitForecast.class)).thenReturn(new WeatherBitForecast());
		assertNotNull(weatherBitService.getWeatherBitForecast("TEST", "1000"));
	}

	@Test
	public void testGetWeatherBitForecastBadRequest() {

		Mockito.when(restClient.getCall(Mockito.anyString())).thenReturn(response);
		Mockito.when(response.getStatus()).thenReturn(400);
		Mockito.when(response.readEntity(WeatherBitErrorResponse.class)).thenReturn(new WeatherBitErrorResponse(""));
		assertNull(weatherBitService.getWeatherBitForecast("TEST", "1000"));
		Mockito.verify(errorService, Mockito.times(1)).getErrors();
	}
}
