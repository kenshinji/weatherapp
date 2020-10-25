package com.tenera.weatherapp.service;

import com.tenera.weatherapp.dto.OpenWeatherResponse;
import com.tenera.weatherapp.dto.WeatherCondition;
import com.tenera.weatherapp.dto.WeatherData;
import com.tenera.weatherapp.dto.WeatherHistory;
import com.tenera.weatherapp.repository.WeatherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${current.weather.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherData queryCurrentWeather(String location) {

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("q", location)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey).build();
        ResponseEntity<OpenWeatherResponse> response = restTemplate
                .getForEntity(builder.toUriString(), OpenWeatherResponse.class);


        double temp = Objects.requireNonNull(response.getBody()).getMain().getTemp();
        double pressure = response.getBody().getMain().getPressure();

        WeatherCondition weatherCondition = response.getBody().getWeather()
                .stream().filter(w -> w.getMain().equals("Rain")
                        || w.getMain().equals("Thunderstorm")
                        || w.getMain().equals("Drizzle"))
                .findAny().orElse(null);

        boolean umbrella = weatherCondition != null;
        WeatherData weatherData = new WeatherData(temp,pressure,umbrella);
        weatherRepository.save(weatherData);
        return weatherData;
    }

    public WeatherHistory queryHistory(String location) {

        List<WeatherData> lastFiveQueries = weatherRepository.findTop5ByOrderByIdDesc()
                .stream().collect(Collectors.toList());

        // Calculate avg values
        double avgTemp = lastFiveQueries.stream().mapToDouble(WeatherData::getTemp)
                .average().orElse(Double.NaN);
        double avgPressure = lastFiveQueries.stream().mapToDouble(WeatherData::getPressure)
                .average().orElse(Double.NaN);

        // set all above values to WeatherHistory
        return new WeatherHistory(avgTemp, avgPressure, lastFiveQueries);
    }
}
