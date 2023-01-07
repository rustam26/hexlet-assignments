package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;

import exercise.repository.UserRepository;
import exercise.model.User;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
@DataSet("users.yml")

public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    void testUnauthorizedRootPage() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void testUnauthorized() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/users"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(401);
    }

    @Test
    void testGetPostWithIncorrectPassword() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(
                get("/users")
                    .header("Authorization", "Basic UGV0cjoxMjM0NTY=")
            )
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(401);
    }

    @Test
    void testGetPostWithCorrectPassword() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(
                get("/users")
                    .header("Authorization", "Basic QWxleGlzOjEyMzQ1")
            )
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Alexis", "Johny_123");
    }

    @Test
    void testCreateUser() throws Exception {
        String content = "{\"username\": \"Petr_12\", \"email\": \"petrilo@yandex.ru\", \"password\": \"mypass\"}";

        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        // Проверяем, что пользователь добавился в базу данных
        User actualUser = userRepository.findByEmail("petrilo@yandex.ru").get();
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getUsername()).isEqualTo("Petr_12");

        // Проверяем, что пароль хранится в базе в зашифрованном виде
        assertThat(actualUser.getPassword()).isNotEqualTo("mypass");

        // Проверяем, что новый пользователь успешно проходит аутентификацию
        MockHttpServletResponse response = mockMvc
            .perform(
                get("/users")
                    .header("Authorization", "Basic UGV0cl8xMjpteXBhc3M=")
            )
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }
}
