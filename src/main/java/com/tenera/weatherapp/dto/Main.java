package com.tenera.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {

    private double temp;
    private double pressure;

    public Main(double temp, double pressure) {
        this.temp = temp;
        this.pressure = pressure;
    }

}
