package com.example.weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.weather.model.City;
import com.example.weather.model.CurrentWeatherInfo;
import com.example.weather.model.ForecastWeatherInfo;
import com.example.weather.model.ListWeather;
import com.example.weather.model.OpenWeatherMapCurrent;
import com.example.weather.model.OpenWeatherMapData;
import com.example.weather.model.OpenWeatherMapForecast;
import com.example.weather.model.WeatherBitCurrent;
import com.example.weather.model.WeatherBitCurrentData;
import com.example.weather.model.WeatherBitDescription;
import com.example.weather.model.WeatherBitForecast;
import com.example.weather.model.WeatherBitTempForecast;

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
