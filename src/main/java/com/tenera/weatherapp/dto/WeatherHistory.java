package com.tenera.weatherapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherHistory {
    private double avgTemp;
    private double avgPresure;
    private List<WeatherData> history;

    public WeatherHistory(double avgTemp, double avgPresure, List<WeatherData> history) {
        this.avgTemp = avgTemp;
        this.avgPresure = avgPresure;
        this.history = history;
    }
}
