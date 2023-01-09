package exercise.calculator;

import org.springframework.stereotype.Component;
import exercise.Inspect;

@Component
@Inspect(level = "info")
public class CalculatorImpl implements Calculator {
    public int sum(int a, int b) {
        return a + b;
    }

    public int mult(int a, int b) {
        return a * b;
    }
}
