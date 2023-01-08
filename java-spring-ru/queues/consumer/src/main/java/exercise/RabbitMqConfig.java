package exercise;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // BEGIN
    @Bean
    public Queue queue() {
        // Задаём имя очереди
        return new Queue("queue", false);
    }
    // END
}
