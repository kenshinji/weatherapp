package com.tenera.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    private double temp;
    private double pressure;
    private boolean umbrella;

    public WeatherData(double temp, double pressure, boolean umbrella) {
        this.temp = temp;
        this.pressure = pressure;
        this.umbrella = umbrella;
    }

}
