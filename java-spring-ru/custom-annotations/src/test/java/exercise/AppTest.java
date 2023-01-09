package exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.system.CapturedOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.context.ApplicationContext;

import exercise.calculator.Calculator;
import exercise.calculator.CalculatorImpl;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  ApplicationContext ctx;


    @Test
    void testSum(CapturedOutput output) throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/sum?a=5&b=10"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo("15");
        assertThat(output).contains("Was called method: sum() with arguments: [5, 10]");
    }

    @Test
    void testMult(CapturedOutput output) throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/mult?a=5&b=10"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo("50");
        assertThat(output).contains("Was called method: mult() with arguments: [5, 10]");
    }

    // Проверяем, что логгирование происходит не в контролере,
    // а при вызове методов класса, отмеченного аннотацией
    @Test
    void testContext(CapturedOutput output) throws Exception {

        // Проверяем, что методы исходного класса калькулятора не содержат логгирование
        Calculator initialCalc = new CalculatorImpl();
        initialCalc.sum(3, 4);
        assertThat(output).doesNotContain("Was called method: sum() with arguments: [3, 4]");

        initialCalc.mult(3, 4);
        assertThat(output).doesNotContain("Was called method: mult() with arguments: [3, 4]");

        // Получаем бин калькулятор из контекста приложения
        Calculator calculatorInContext = ctx.getBean(Calculator.class);

        // Проверяем, что при вызове его методов происходит логгирование
        calculatorInContext.sum(3, 4);
        assertThat(output).contains("Was called method: sum() with arguments: [3, 4]");

        calculatorInContext.mult(4, 5);
        assertThat(output).contains("Was called method: mult() with arguments: [4, 5]");
    }
}
