package com.tenera.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {

    private Main main;

    private List<WeatherCondition> weather;

    public OpenWeatherResponse(Main main, List<WeatherCondition> weather) {
        this.main = main;
        this.weather = weather;
    }
}
