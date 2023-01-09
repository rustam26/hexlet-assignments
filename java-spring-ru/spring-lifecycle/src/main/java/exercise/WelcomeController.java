package exercise;

import exercise.daytimes.Daytime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {
    private final Meal meal;

    private final Daytime daytime;

    public WelcomeController(Meal meal, Daytime daytime) {
        this.meal = meal;
        this.daytime = daytime;
    }

    @GetMapping("/daytime")
    public String getDaytime() {
        return "It is " + daytime.getName() + " now. Enjoy your " + meal.getMealForDaytime(daytime.getName());
    }
}
// END
