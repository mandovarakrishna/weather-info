package com.webservice.weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.webservice.weather.model.City;
import com.webservice.weather.model.CurrentWeatherInfo;
import com.webservice.weather.model.ForecastWeatherInfo;
import com.webservice.weather.model.ListWeather;
import com.webservice.weather.model.OpenWeatherMapCurrent;
import com.webservice.weather.model.OpenWeatherMapData;
import com.webservice.weather.model.OpenWeatherMapForecast;
import com.webservice.weather.model.WeatherBitCurrent;
import com.webservice.weather.model.WeatherBitCurrentData;
import com.webservice.weather.model.WeatherBitDescription;
import com.webservice.weather.model.WeatherBitForecast;
import com.webservice.weather.model.WeatherBitTempForecast;
import com.webservice.weather.service.WeatherHelperService;

@ExtendWith(MockitoExtension.class)
public class WeatherHelperServiceTest {
	
	@InjectMocks
	WeatherHelperService weatherHelperService;
	
	@Test
	public void testAverageOfCurrentWeather() {
		
		OpenWeatherMapCurrent openWeatherMapCurrent = new OpenWeatherMapCurrent();
		
		OpenWeatherMapData openWeatherMapData = new OpenWeatherMapData();
		openWeatherMapData.setFeels_like(322.12);
		openWeatherMapData.setTemp(302.2);
		openWeatherMapCurrent.setMain(openWeatherMapData);
		openWeatherMapCurrent.setName("Test");
		
		WeatherBitCurrent weatherBitCurrent = new WeatherBitCurrent();
		
		WeatherBitDescription description = new WeatherBitDescription();
		description.setDescription("Test");
		List<WeatherBitCurrentData> list = new ArrayList<>();
		WeatherBitCurrentData weatherBitCurrentData = new WeatherBitCurrentData();
		weatherBitCurrentData.setTemp(32);
		weatherBitCurrentData.setApp_temp(35);
		weatherBitCurrentData.setWeather(description);
		list.add(weatherBitCurrentData);
		weatherBitCurrent.setData(list);
		
		CurrentWeatherInfo currentWeatherInfo = weatherHelperService.averageOfCurrentWeather(openWeatherMapCurrent, weatherBitCurrent);
		assertEquals("Test", currentWeatherInfo.getCity());
		assertEquals("Test", currentWeatherInfo.getWeather());
		assertEquals("30.53", currentWeatherInfo.getAvgTemp());
		assertEquals("41.99", currentWeatherInfo.getAvgApparent());
	}
	
	@Test
	public void testAverageOfForecastWeather() {
		
		OpenWeatherMapForecast openWeatherMapForecast = new OpenWeatherMapForecast();
		
		City city = new City();
		city.setName("Test");
		OpenWeatherMapData mapData = new OpenWeatherMapData();
		mapData.setFeels_like(302);
		mapData.setTemp(301);
		List<ListWeather> list = new ArrayList<>();
		ListWeather listWeather = new ListWeather();
		listWeather.setDt_txt(LocalDateTime.of(2020, 12, 03, 00, 00, 00));
		listWeather.setMain(mapData);
		list.add(listWeather);
		openWeatherMapForecast.setCity(city);
		openWeatherMapForecast.setList(list);
		
		WeatherBitForecast weatherBitForecast = new WeatherBitForecast();
		
		List<WeatherBitTempForecast> bitForecasts = new ArrayList<>();
		
		WeatherBitDescription weatherBitDescription = new WeatherBitDescription();
		weatherBitDescription.setDescription("Cloud");
		WeatherBitTempForecast bitTempForecast = new WeatherBitTempForecast();
		bitTempForecast.setTemp(23);
		bitTempForecast.setWeatherBitDescription(weatherBitDescription);
		bitTempForecast.setValid_date(LocalDate.of(2020, 12, 03));
		bitForecasts.add(bitTempForecast);
		weatherBitForecast.setCity_name("Test");
		weatherBitForecast.setData(bitForecasts);
		
		ForecastWeatherInfo forecastWeatherInfo = weatherHelperService.averageOfForecastWeather(openWeatherMapForecast, weatherBitForecast);
		
		assertEquals("Test", forecastWeatherInfo.getCity());
		
	}

}
