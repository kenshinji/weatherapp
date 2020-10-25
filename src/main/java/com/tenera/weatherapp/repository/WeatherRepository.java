package com.tenera.weatherapp.repository;

import com.tenera.weatherapp.dto.WeatherData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepository extends CrudRepository<WeatherData, Long> {

    List<WeatherData> findTop5ByOrderByIdDesc();

}
