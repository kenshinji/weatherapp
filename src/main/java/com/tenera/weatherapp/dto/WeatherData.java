package com.tenera.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class WeatherData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long id;

    private double temp;
    private double pressure;
    private boolean umbrella;

    public WeatherData(double temp, double pressure, boolean umbrella) {
        this.temp = temp;
        this.pressure = pressure;
        this.umbrella = umbrella;
    }

    public WeatherData() {

    }
}
