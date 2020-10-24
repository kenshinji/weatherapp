package com.tenera.weatherapp.service;

import com.tenera.weatherapp.dto.Weather;
import com.tenera.weatherapp.dto.WeatherHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    public WeatherHistory queryHistory(String location) {

        // call repository to get last 5 history data
        List<Weather> history = new ArrayList<Weather>();

        // Calculate avg values
        Double avgTemp = 1.0;
        Double avgPresure = 2.0;

        // set all above values to WeatherHistory
        return new WeatherHistory(avgTemp, avgPresure, history);
    }
}
