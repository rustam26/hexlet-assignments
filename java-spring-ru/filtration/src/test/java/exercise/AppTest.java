package exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import exercise.model.User;

import java.util.List;

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
@DataSet("users.yml")
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAllUsers() throws Exception {

        MockHttpServletResponse response = mockMvc
                .perform(get("/users"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());

        List<User> actualUsers = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<List<User>>() {
                }
        );

        assertThat(actualUsers.size()).isEqualTo(100);
    }

    @Test
    void testFilterByFirstName() throws Exception {

        MockHttpServletResponse response = mockMvc
                .perform(get("/users?firstName=ale"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());

        List<User> actualUsers = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<List<User>>() {
                }
        );

        User actualUser = actualUsers.get(0);
        assertThat(actualUsers.size()).isEqualTo(1);
        assertThat(actualUser.getFirstName()).isEqualTo("Lauralee");
        assertThat(actualUser.getLastName()).isEqualTo("Flowitt");
    }

    @Test
    void testFilterByLastName() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/users?lastName=son"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());

        List<User> actualUsers = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<List<User>>() {
                }
        );

        User actualUser = actualUsers.get(0);
        assertThat(actualUsers.size()).isEqualTo(1);
        assertThat(actualUser.getFirstName()).isEqualTo("Bernadine");
        assertThat(actualUser.getLastName()).isEqualTo("Hardison");
    }

    @Test
    void testFilterByFirstAndLastName() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/users?firstName=t&lastName=w"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());

        List<User> actualUsers = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<List<User>>() {
                }
        );

        User actualUser = actualUsers.get(0);
        assertThat(actualUsers.size()).isEqualTo(1);
        assertThat(actualUser.getFirstName()).isEqualTo("Colette");
        assertThat(actualUser.getLastName()).isEqualTo("Wyvill");
    }

    // Тест для дополнительной задачи
    // BEGIN

    @Test
    void testFilterByFirstAndLastNameAndEmailAndProfessionAndGender() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/users/dop?firstName=a&lastName=a&email=a&profession=Chemical&gender=Male"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());

        List<User> actualUsers = objectMapper.readValue(
                response.getContentAsString(),
                new TypeReference<List<User>>() {
                }
        );

        User actualUser = actualUsers.get(0);
        assertThat(actualUsers.size()).isEqualTo(1);
        assertThat(actualUser.getFirstName()).isEqualTo("Bradney");
        assertThat(actualUser.getLastName()).isEqualTo("Grandin");
        assertThat(actualUser.getEmail()).isEqualTo("bgrandin11@guardian.co.uk");
        assertThat(actualUser.getGender()).isEqualTo("Male");
        assertThat(actualUser.getProfession()).isEqualTo("Chemical Engineer");
    }
    // END
}
