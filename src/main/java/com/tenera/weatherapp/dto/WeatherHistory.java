package com.tenera.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
public class WeatherHistory {

    @JsonProperty(value = "avg_temp")
    private double avgTemp;

    @JsonProperty(value = "avg_pressure")
    private double avgPressure;

    @JsonProperty(value = "history")
    private List<WeatherData> history;

    public WeatherHistory(double avgTemp, double avgPressure, List<WeatherData> history) {
        this.avgTemp = avgTemp;
        this.avgPressure = avgPressure;
        this.history = history;
    }
}
