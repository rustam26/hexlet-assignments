package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import exercise.daytimes.Daytime;
import exercise.daytimes.Day;
import exercise.daytimes.Morning;
import exercise.daytimes.Evening;
import exercise.daytimes.Night;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.context.ApplicationContext;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  ApplicationContext ctx;

    private Daytime getDayTime() {
        int hour = LocalDateTime.now().getHour();

        if (hour >= 6 && hour < 12) {
            return new Morning();
        }
        if (hour >= 12 && hour < 18) {
            return new Day();
        }
        if (hour >= 18 && hour < 23) {
            return new Evening();
        }
        return new Night();
    }

    @Test
    void testGetGreeting() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/daytime"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
            .contains(getDayTime().getName());
    }

    @Test
    void testContext() throws Exception {

        assertThat(ctx.containsBeanDefinition("meal"));
        Daytime currentDaytime = getDayTime();
        // Проверяем, что в контекст добавился тот бин, который соответствует времени суток
        assertThat(ctx.getBean(Daytime.class)).isInstanceOf(currentDaytime.getClass());
    }
}
