package com.example.weather.error;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Named;
import javax.inject.Singleton;

//@Singleton
@Named
public class ErrorService {

	List<WeatherError> errors = new CopyOnWriteArrayList<>();

	public void intializeErrors() {
		if(!errors.isEmpty()) {
			errors.clear();
		}
	}

	public void addError(String message, String type) {

		errors.add(new WeatherError(message, type));
	}

	public List<WeatherError> getErrors() {
		return errors;
	}
}
