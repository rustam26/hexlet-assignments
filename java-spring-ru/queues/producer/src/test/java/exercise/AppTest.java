package exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;

import io.github.cmduque.amqp.mock.AMQPServerMock;
import static io.github.cmduque.amqp.mock.dto.ServerConfig.defaultConfig;
import io.github.cmduque.amqp.mock.dto.Message;

import java.util.List;

import exercise.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
@DataSet("users.yml")
public class AppTest {

    private static AMQPServerMock server;
    private static int mockServerPort = 5611;
    private String key = "exchange.key";

    @BeforeAll
    public static void beforeAll() {
        server = new AMQPServerMock(defaultConfig().withPort(mockServerPort));
        server.start();
    }

    @AfterAll
    public static void afterAll() {
        server.stop();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    void testCreateUser() throws Exception {
        String content = "{\"name\": \"Tirion\"}";
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);
        assertThat(userRepository.existsByName("Tirion")).isTrue();

        List<Message> receivedMessages = server.getAllReceivedMessages(key);
        assertThat(receivedMessages.size()).isEqualTo(1);
        String message = new String(receivedMessages.get(0).getBody());
        assertThat(message).isEqualTo("User Tirion has been registered");
    }

    @Test
    void testDeleteUser() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(delete("/users/1"))
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);
        assertThat(userRepository.existsById(1L)).isFalse();

        List<Message> receivedMessages = server.getAllReceivedMessages(key);
        assertThat(receivedMessages.size()).isEqualTo(2);
        String message = new String(receivedMessages.get(1).getBody());
        assertThat(message).isEqualTo("User Jack has been deleted");
    }
}
