package com.tenera.weatherapp.service;

import com.tenera.weatherapp.dto.OpenWeatherResponse;
import com.tenera.weatherapp.dto.WeatherCondition;
import com.tenera.weatherapp.dto.WeatherData;
import com.tenera.weatherapp.dto.WeatherHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class WeatherService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${current.weather.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

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

        return new WeatherData(temp,pressure,umbrella);
    }

    public WeatherHistory queryHistory(String location) {

        // call repository to get last 5 history data
        List<WeatherData> history = new ArrayList<>();

        // Calculate avg values
        double avgTemp = 1.0;
        double avgPressure = 2.0;

        // set all above values to WeatherHistory
        return new WeatherHistory(avgTemp, avgPressure, history);
    }
}
