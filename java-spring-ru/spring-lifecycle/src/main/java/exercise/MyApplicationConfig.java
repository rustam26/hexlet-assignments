package exercise;

import java.time.LocalDateTime;
import java.time.LocalTime;

import exercise.daytimes.Daytime;
import exercise.daytimes.Morning;
import exercise.daytimes.Day;
import exercise.daytimes.Evening;
import exercise.daytimes.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BEGIN
@Configuration
public class MyApplicationConfig {

    @Bean
    public Daytime getMorning() {
        LocalTime localTimeNow = LocalDateTime.now()
                .toLocalTime();
        if (localTimeNow.isAfter(LocalTime.of(6, 0, 0)) &&
                localTimeNow.isBefore(LocalTime.of(12, 0, 0))) {
            return new Morning();
        } else if (localTimeNow.isAfter(LocalTime.of(12, 0, 0)) &&
                localTimeNow.isBefore(LocalTime.of(18, 0, 0))) {
            return new Day();
        } else if (localTimeNow.isAfter(LocalTime.of(18, 0, 0)) &&
                localTimeNow.isBefore(LocalTime.of(23, 0, 0))) {
            return new Evening();
        } else if (localTimeNow.isAfter(LocalTime.of(23, 0, 0)) &&
                localTimeNow.isBefore(LocalTime.of(6, 0, 0))) {
            return new Night();
        }
        return null;
    }

}
// END
