package exercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.CityNotFoundException;
import exercise.dto.CityWeatherDTO;
import exercise.dto.WeatherDTO;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    private final ObjectMapper mapper = new ObjectMapper();

    // BEGIN
    @GetMapping("/cities/{id}")
    public WeatherDTO getWeather(@PathVariable("id") long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id + " not found"));
        try {
            return mapper.readValue(weatherService.getWeather(city.getName()), WeatherDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search")
    public List<CityWeatherDTO> getWeather(@RequestParam(required = false) String name) {
        List<City> cities;
        if (name == null || name.isEmpty()) {
            cities = cityRepository.getAll();
        } else {
            cities = cityRepository.findByNameIsContainingIgnoreCase(name);
        }

        return cities.stream()
                .map(city -> {
                    try {
                        return mapper.readValue(weatherService.getWeather(city.getName()), WeatherDTO.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(weatherDTO -> new CityWeatherDTO(weatherDTO.getTemperature(), weatherDTO.getName()))
                .collect(Collectors.toList());

    }
    // END
}

