package com.tenera.weatherapp.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class WeatherHistory {
    private double avgTemp;
    private double avgPresure;
    private List<Weather> history;

    public WeatherHistory(double avgTemp, double avgPresure, List<Weather> history) {
        this.avgTemp = avgTemp;
        this.avgPresure = avgPresure;
        this.history = history;
    }
}
