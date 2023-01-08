package exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import io.github.cmduque.amqp.mock.AMQPServerMock;
import com.rabbitmq.client.AMQP;
import static io.github.cmduque.amqp.mock.dto.ServerConfig.defaultConfig;
import io.github.cmduque.amqp.mock.dto.Message;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


@SpringBootTest
@AutoConfigureMockMvc
public class AppTest {

    private static AMQPServerMock server;
    private static int mockServerPort = 5612;
    private String queue = "queue";
    private ObjectMapper mapper = new ObjectMapper();

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


    @Test
    void testGetMessages() throws Exception {

        String message = "Test message";

        server.publish(new Message("", queue, new AMQP.BasicProperties.Builder().build(), message.getBytes()));
        server.publish(new Message("", queue, new AMQP.BasicProperties.Builder().build(), message.getBytes()));

        MockHttpServletResponse response = mockMvc
            .perform(get("/messages"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);

        List<String> messages = mapper.readValue(response.getContentAsString(), new TypeReference<List<String>>() { });
        assertThat(messages.size()).isEqualTo(2);
        assertThat(messages.get(0)).isEqualTo(message);
    }
}
