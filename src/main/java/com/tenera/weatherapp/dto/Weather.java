package com.tenera.weatherapp.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Weather {
    private double temp;
    private double pressure;
    private boolean umbrella;
}
