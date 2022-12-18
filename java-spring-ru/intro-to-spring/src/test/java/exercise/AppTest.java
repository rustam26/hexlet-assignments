package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRootPage() throws Exception {
        String body = this.restTemplate.getForObject(
            "http://localhost:" + port + "/",
            String.class
        );
        assertThat(body).contains("Welcome to Spring");
    }

    @Test
    public void testHelloPage1() throws Exception {
        String body = this.restTemplate.getForObject(
            "http://localhost:" + port + "/hello",
            String.class
        );
        assertThat(body).contains("Hello, World");
    }

    @Test
    public void testHelloPage2() throws Exception {
        String body = this.restTemplate.getForObject(
            "http://localhost:" + port + "/hello?name=Tirion",
            String.class
        );
        assertThat(body).contains("Hello, Tirion");
    }
}
