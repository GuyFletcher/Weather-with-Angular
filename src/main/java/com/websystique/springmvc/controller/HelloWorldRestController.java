package com.websystique.springmvc.controller;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

import com.websystique.springmvc.model.Weather;
import com.websystique.springmvc.service.WeatherService;
 
@RestController
public class HelloWorldRestController {
 
    @Autowired
    WeatherService weatherService;  //Service which will do all data retrieval/manipulation work
 
    
    //-------------------Retrieve Total Forecast--------------------------------------------------------
     
    @RequestMapping(value = "/weather/", method = RequestMethod.GET)
    public ResponseEntity<List<Weather>> listWeather() {
        List<Weather> forecast = weatherService.findForecast();
        if(forecast.isEmpty()){
            return new ResponseEntity<List<Weather>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Weather>>(forecast, HttpStatus.OK);
    }
 
 
    
    //-------------------Retrieve Single Day--------------------------------------------------------
     
    @RequestMapping(value = "/weather/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Weather> getWeather(@PathVariable("id") long id) {
        System.out.println("Fetching Weather with id " + id);
        Weather w = weatherService.findById(id);
        if (w == null) {
            System.out.println("Weather with id " + id + " not found");
            return new ResponseEntity<Weather>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Weather>(w, HttpStatus.OK);
    }
 
     
     
    //-------------------Create Weather--------------------------------------------------------
    
    @RequestMapping(value = "/weather/", method = RequestMethod.POST)
    public ResponseEntity<Void> addFavorite(@RequestBody Weather weather) {
        System.out.println("Adding to favorites: " + weather.getDate());
 
        //add to mysql database
        try{  
        	Class.forName("com.mysql.cj.jdbc.Driver");  
        	Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/weather","root","");    

        	String sql = "INSERT INTO weather (Description, Temp, Date) " + " VALUES (?,?,?)";
        	
        	PreparedStatement preparedStatement = con.prepareStatement(sql);
        	preparedStatement.setString(1, weather.getDescription());
        	preparedStatement.setDouble(2, weather.getTemp());
        	preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(weather.getDate()));
        	preparedStatement.executeUpdate(); 
        	
        	con.close();  
        	} catch(Exception e){ 
        		System.out.println(e);
        	}  
        //weatherService.saveWeather(weather);
 
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }



}