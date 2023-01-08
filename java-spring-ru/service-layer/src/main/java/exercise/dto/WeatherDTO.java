package exercise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDTO {

    private String name;

    private long temperature;

    private String cloudy;

    private long humidity;

    private long wind;

}
