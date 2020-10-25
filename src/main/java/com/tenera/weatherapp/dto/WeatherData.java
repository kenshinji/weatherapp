package com.tenera.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class WeatherData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @JsonIgnore
    private String location;

    private double temp;
    private double pressure;
    private boolean umbrella;

    public WeatherData(String location, double temp, double pressure, boolean umbrella) {
        this.location = location;
        this.temp = temp;
        this.pressure = pressure;
        this.umbrella = umbrella;
    }

    public WeatherData() {

    }
}
