package com.example.weather.error;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named
public class ErrorService {
	
	private static final ThreadLocal<List<WeatherError>> threadLocal = new InheritableThreadLocal<>(); 
	
	public void intializeErrors() {
		List<WeatherError> errors = new CopyOnWriteArrayList<>();
		threadLocal.set(errors);
	}
	
	public void addError(String message, String type) {
		List<WeatherError> errors = threadLocal.get();
		errors.add(new WeatherError(message, type));
	}
	
	public List<WeatherError> getErrors() {
		return threadLocal.get();
	}
}
