package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.Weather;



public interface WeatherService {
	
	Weather findById(long id);
	
	Weather findByName(String name);
	
	void saveWeather(Weather w);

	List<Weather> findForecast(); 
	
	public boolean isWeatherExist(Weather w);
	
}
