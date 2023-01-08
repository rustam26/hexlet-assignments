package exercise;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;


@Component
public class HttpClient {

    private ObjectMapper mapper = new ObjectMapper();

    private int getRandomNumber(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    public String get(String url) {
        String[] urlParts = url.split("v2/cities/");
        String city = urlParts[1];

        Map<String, String> weather = generateWeather(city);

        String json;

        try {
            json = mapper.writeValueAsString(weather);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return json;
    }

    private Map<String, String> generateWeather(String city) {

        String[] cloudy = {"cloudy", "clear", "Partly cloudy"};

        return Map.of(
            "name", city,
            "temperature", String.valueOf(getRandomNumber(0, 35)),
            "cloudy", cloudy[getRandomNumber(0, cloudy.length - 1)],
            "wind", String.valueOf(getRandomNumber(0, 20)),
            "humidity", String.valueOf(getRandomNumber(40, 100))
        );

    }
}
