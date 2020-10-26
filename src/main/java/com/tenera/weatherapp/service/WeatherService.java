package com.tenera.weatherapp.service;

import com.tenera.weatherapp.dto.OpenWeatherResponse;
import com.tenera.weatherapp.dto.WeatherCondition;
import com.tenera.weatherapp.dto.WeatherData;
import com.tenera.weatherapp.dto.WeatherHistory;
import com.tenera.weatherapp.exceptionhandler.ValidationException;
import com.tenera.weatherapp.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WeatherService {

    Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${api.key}")
    private String apiKey;

    @Value("${current.weather.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherData queryCurrentWeather(String location) {

        logger.debug("ENTRY : queryCurrentWeather {}", location);

        if(!validateInput(location)) throw new ValidationException("Bad city name for the request");
        WeatherData weatherData = null;
        try {
            ResponseEntity<OpenWeatherResponse> response = restTemplate
                    .getForEntity(url + "?units=metric&appid=" + apiKey + "&q=" + location, OpenWeatherResponse.class);
            double temp = Objects.requireNonNull(response.getBody()).getMain().getTemp();
            double pressure = response.getBody().getMain().getPressure();

            WeatherCondition weatherCondition = response.getBody().getWeather()
                    .stream().filter(w -> w.getMain().equals("Rain")
                            || w.getMain().equals("Thunderstorm")
                            || w.getMain().equals("Drizzle"))
                    .findAny().orElse(null);

            boolean umbrella = weatherCondition != null;
            // make sure same city name are all saved as lower case into the database to reduce duplications.
            weatherData = new WeatherData(processLocation(location), temp, pressure, umbrella);
            weatherRepository.save(weatherData);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof HttpClientErrorException.NotFound) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "city not found"
                );
            }else{
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "internal server error"
                );
            }
        }
        return weatherData;
    }

    public WeatherHistory queryHistory(String location) {

        logger.debug("ENTRY : queryHistory {}", location);

        if(!validateInput(location)) throw new ValidationException("Bad city name for the request");

        List<WeatherData> lastFiveQueries = new ArrayList<>(weatherRepository.findTop5ByLocationOrderByIdDesc(processLocation(location)));
        if (lastFiveQueries.size() == 0) {
            return new WeatherHistory(0.0, 0.0, lastFiveQueries);
        }

        // Calculate avg values
        double avgTemp = lastFiveQueries.stream().mapToDouble(WeatherData::getTemp)
                .average().orElse(Double.NaN);
        double avgPressure = lastFiveQueries.stream().mapToDouble(WeatherData::getPressure)
                .average().orElse(Double.NaN);

        // set all above values to WeatherHistory
        return new WeatherHistory(avgTemp, avgPressure, lastFiveQueries);
    }

    private boolean validateInput(String location) {
        // consider location only has letters
        boolean allLetters = location.chars().allMatch(Character::isLetter);
        if(allLetters) return true;

        // location consist of city and country
        String[] loc = location.split(",");
        if (loc.length != 2) {
            return false;
        }else{
            return validateInput(loc[0]) && validateInput(loc[1]);
        }
    }

    private String processLocation(String location) {
        String[] loc = location.split(",");
        if (loc.length == 2) {
            return loc[0].toLowerCase();
        }else{
            return location;
        }
    }
}
