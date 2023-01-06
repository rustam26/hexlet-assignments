package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    void testGetUsers() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/users"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("John", "Smith");
        assertThat(response.getContentAsString()).contains("Jack", "Doe");
    }

    @Test
    void testCreateUser() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"Jackson\", \"lastName\": \"Bind\", \"email\": \"test@test.com\"}")
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(201);

        MockHttpServletResponse response = mockMvc
            .perform(get("/users"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Jackson", "Bind", "test@test.com");
    }

    @Test
    void testGetUser() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/users/1"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("John", "Smith");
    }

    @Test
    void testUpdateUser1() throws Exception {

        MockHttpServletResponse responsePost = mockMvc
            .perform(
                patch("/users/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"Will\", \"lastName\": \"Walker\", \"email\": \"test@test.com\"}")
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/users"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Will", "Walker", "test@test.com");
        assertThat(response.getContentAsString()).doesNotContain("John", "Smith");
    }

    @Test
    void testUpdateUserWithNonExistedId() throws Exception {

        MockHttpServletResponse responsePost = mockMvc
            .perform(
                patch("/users/100")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"Will\", \"lastName\": \"Walker\", \"email\": \"test@test.com\"}")
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(404);

        MockHttpServletResponse response = mockMvc
            .perform(get("/users"))
            .andReturn()
            .getResponse();

        assertThat(response.getContentAsString()).doesNotContain("Will", "Walker", "test@test.com");
    }

    @Test
    void testDeleteUser() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(delete("/users/1"))
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/users"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).doesNotContain("John", "Smith");
    }

    @Test
    void testSwaggerUiPage() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(get("/v3/api-docs"))
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);
    }
}
