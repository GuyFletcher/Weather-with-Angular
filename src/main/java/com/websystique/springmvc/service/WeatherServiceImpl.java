package com.websystique.springmvc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

import com.websystique.springmvc.model.Weather;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Weather> forecast;
	
	static{
		try {
			forecast= populateForecast();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Weather> findForecast() {
		return forecast;
	}
	
	public Weather findById(long id) {
		for(Weather w : forecast){
			if(w.getId() == id){
				return w;
			}
		}
		return null;
	}
	
	public Weather findByName(String name) {
		for(Weather w : forecast){
			if(w.getDescription().equalsIgnoreCase(name)){
				return w;
			}
		}
		return null;
	}
	
	public void saveWeather(Weather w) {
		w.setId(counter.incrementAndGet());
		forecast.add(w);
	}

	public boolean isWeatherExist(Weather w) {
		return findByName(w.getDescription())!=null;
	}

	private static List<Weather> populateForecast() throws IOException, JSONException{
		List<Weather> w = new ArrayList<Weather>();
		
		//api call to weatherapi
		String url = "http://api.openweathermap.org/data/2.5/forecast?APPID=ab90019a46ee4ed511f87895b9903f08&zip=05401,us&units=Imperial";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		JSONObject json = new JSONObject(response.toString());
		
		for(int i = 0; i < 5; i++) {
			JSONObject data = json.getJSONArray("list").getJSONObject(i);
			w.add(new Weather(counter.incrementAndGet(), data.getJSONArray("weather").getJSONObject(0).getString("description"),  data.getJSONObject("main").getDouble("temp"), data.getString("dt_txt")));
		}
		
		//w.add(new Weather(counter.incrementAndGet(), "Rain", 78.8, "1234"));
		
		return w;
	}

}
