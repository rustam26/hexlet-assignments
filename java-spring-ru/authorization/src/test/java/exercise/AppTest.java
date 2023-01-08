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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;

import exercise.repository.UserRepository;
import exercise.model.User;
import exercise.model.UserRole;

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
    void testUnauthenticatedRootPage() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void testUnauthenticated() throws Exception {
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
                    .header("Authorization", "Basic UGV0cjoxMjM0N")
            )
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(401);
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

        // Проверяем, что новый пользователь создаётся с ролью USER
        assertThat(actualUser.getRole()).isEqualTo(UserRole.USER);

        // Проверяем, что новый пользователь успешно проходит аутентификацию
        MockHttpServletResponse response = mockMvc
            .perform(
                get("/users")
                    .header("Authorization", "Basic cGV0cmlsb0B5YW5kZXgucnU6bXlwYXNz")
            )
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    // Проверяем полномочия пользователя

    // Проверяем, что пользователь с ролью USER может видеть список пользователей
    // и просматривать конкретного пользователя
    @Test
    void testUserCanShowUsers() throws Exception {
        MockHttpServletResponse response1 = mockMvc
            .perform(
                get("/users")
                    .header("Authorization", "Basic YWxleGlzQGdtYWlsLmNvbToxMjM0NQ==")
            )
            .andReturn()
            .getResponse();

        assertThat(response1.getStatus()).isEqualTo(200);
        assertThat(response1.getContentAsString()).contains("Alexis", "alexis@gmail.com");
        assertThat(response1.getContentAsString()).contains("Admin", "admin@gmail.com");

        MockHttpServletResponse response2 = mockMvc
            .perform(
                get("/users/1")
                    .header("Authorization", "Basic YWxleGlzQGdtYWlsLmNvbToxMjM0NQ==")
            )
            .andReturn()
            .getResponse();

        assertThat(response2.getStatus()).isEqualTo(200);
        assertThat(response2.getContentAsString()).contains("Alexis", "alexis@gmail.com");
    }

    // Проверяем, что пользователь ролью USER не может удалять пользователей
    @Test
    void testUserCannotDeleteUser() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(
                delete("/users/2")
                    .header("Authorization", "Basic YWxleGlzQGdtYWlsLmNvbToxMjM0NQ==")
            )
            .andReturn()
            .getResponse();

        // Проверяем, что статус ответа 403 Forbidden
        // Код ответа 403 Forbidden указывает, что сервер понял запрос, но отказывается его авторизовать.
        assertThat(response.getStatus()).isEqualTo(403);
    }

    // Проверяем полномочия администратора (роль ADMIN)

    // Администратор может просматривать данные пользователей
    @Test
    void testAdminCanShowUsers() throws Exception {
        MockHttpServletResponse response1 = mockMvc
            .perform(
                get("/users")
                    .header("Authorization", "Basic YWRtaW5AZ21haWwuY29tOmFkbWluX3Bhc3M=")
            )
            .andReturn()
            .getResponse();

        assertThat(response1.getStatus()).isEqualTo(200);
        assertThat(response1.getContentAsString()).contains("Alexis", "alexis@gmail.com");
        assertThat(response1.getContentAsString()).contains("Admin", "admin@gmail.com");

        MockHttpServletResponse response2 = mockMvc
            .perform(
                get("/users/1")
                    .header("Authorization", "Basic YWRtaW5AZ21haWwuY29tOmFkbWluX3Bhc3M=")
            )
            .andReturn()
            .getResponse();

        assertThat(response2.getStatus()).isEqualTo(200);
        assertThat(response2.getContentAsString()).contains("Alexis", "alexis@gmail.com");
    }

    @Test
    void testAdminCanDeleteUser() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(
                delete("/users/1")
                    .header("Authorization", "Basic YWRtaW5AZ21haWwuY29tOmFkbWluX3Bhc3M=")
            )
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);

        // Проверяем, что пользователь удалился из базы данных
        boolean isUserPresent = userRepository.findById(1L).isPresent();
        assertThat(isUserPresent).isFalse();
    }
}
