package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;
import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
@DataSet("cities.yml")
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAllCities() throws Exception {

        MockHttpServletResponse response = mockMvc
            .perform(get("/search"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());


        List<Map<String, String>> actualCities = objectMapper.readValue(
            response.getContentAsString(),
            new TypeReference<List<Map<String, String>>>() { }
        );

        // Проверяем, что без фильтра вернулся полный список городов
        assertThat(actualCities.size()).isEqualTo(5);

        // Проверяем, что города отсортированы по имени
        Map<String, String> city = actualCities.get(0);
        assertThat(city.get("name")).isEqualTo("Bagdad");
        // Проверяем, что данные содержат температуру в городе
        assertThat(StringUtils.isNumeric(city.get("temperature"))).isTrue();
    }

    @Test
    void testFilteredCities() throws Exception {

        MockHttpServletResponse response = mockMvc
            .perform(get("/search?name=m"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Moscow", "Marakesh");

        List<Map<String, String>> actualCities = objectMapper.readValue(
            response.getContentAsString(),
            new TypeReference<List<Map<String, String>>>() { }
        );

        assertThat(actualCities.size()).isEqualTo(2);

        Map<String, String> city = actualCities.get(0);
        assertThat(StringUtils.isNumeric(city.get("temperature"))).isTrue();
    }

    @Test
    void testCityWeather() throws Exception {

        MockHttpServletResponse response = mockMvc
            .perform(get("/cities/1"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());

        Map<String, String> actualCity = objectMapper.readValue(
            response.getContentAsString(),
            new TypeReference<Map<String, String>>() { }
        );

        assertThat(actualCity.get("name")).isEqualTo("Moscow");
        // Проверяем, что вывод содержит информацию об облачности, температуре,
        // ветре и влажности
        assertThat(actualCity).containsKey("cloudy");
        assertThat(StringUtils.isNumeric(actualCity.get("temperature"))).isTrue();
        assertThat(StringUtils.isNumeric(actualCity.get("wind"))).isTrue();
        assertThat(StringUtils.isNumeric(actualCity.get("humidity"))).isTrue();
    }
}
