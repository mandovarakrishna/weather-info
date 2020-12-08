package com.example.weather.service;

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

import com.example.weather.error.ErrorService;
import com.example.weather.error.OpenWeatherErrorResponse;
import com.example.weather.model.OpenWeatherMapCurrent;
import com.example.weather.model.OpenWeatherMapForecast;
import com.example.weather.rest.RestClient;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherMapServiceTest {

	@InjectMocks
	OpenWeatherMapService openWeatherMapService;

	@Mock
	RestClient restClient;

	@Mock
	ErrorService errorService;

	@Mock
	Response response;

	@BeforeEach
	public void setUp() {

		ReflectionTestUtils.setField(openWeatherMapService, "openWeatherMapCurrentUrl", "http://test");
		ReflectionTestUtils.setField(openWeatherMapService, "openWeatherMapForecastUrl", "http://test");
		ReflectionTestUtils.setField(openWeatherMapService, "openWeatherMapValue", "test");
	}

	@Test
	public void testGetOpenWeatherMapCurrent() {

		Mockito.when(restClient.getCall(Mockito.anyString())).thenReturn(response);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(OpenWeatherMapCurrent.class)).thenReturn(new OpenWeatherMapCurrent());
		assertNotNull(openWeatherMapService.getOpenWeatherMapCurrent("TEST", "1000"));
	}

	@Test
	public void testGetOpenWeatherMapCurrentBadRequest() {

		Mockito.when(restClient.getCall(Mockito.anyString())).thenReturn(response);
		Mockito.when(response.getStatus()).thenReturn(400);
		Mockito.when(response.readEntity(OpenWeatherErrorResponse.class))
				.thenReturn(new OpenWeatherErrorResponse("", ""));
		assertNull(openWeatherMapService.getOpenWeatherMapCurrent("TEST", "1000"));
		Mockito.verify(errorService, Mockito.times(1)).getErrors();
	}

	@Test
	public void testGetOpenWeatherMapForecast() {

		Mockito.when(restClient.getCall(Mockito.anyString())).thenReturn(response);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.readEntity(OpenWeatherMapForecast.class)).thenReturn(new OpenWeatherMapForecast());
		assertNotNull(openWeatherMapService.getOpenWeatherMapForecast("TEST", "1000"));
	}

	@Test
	public void testGetOpenWeatherMapForecastBadRequest() {

		Mockito.when(restClient.getCall(Mockito.anyString())).thenReturn(response);
		Mockito.when(response.getStatus()).thenReturn(400);
		Mockito.when(response.readEntity(OpenWeatherErrorResponse.class))
				.thenReturn(new OpenWeatherErrorResponse("", ""));
		assertNull(openWeatherMapService.getOpenWeatherMapForecast("TEST", "1000"));
		Mockito.verify(errorService, Mockito.times(1)).getErrors();
	}
}
