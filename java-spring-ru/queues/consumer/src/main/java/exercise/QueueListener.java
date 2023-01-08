package exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.List;
import java.util.ArrayList;


@Component
@EnableRabbit
public class QueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueListener.class);

    // Для наглядности в список будут добавляться все сообщения,
    // которые получены из очереди
    // Так их можно будет легко просмотреть
    private List<String> messages = new ArrayList<>();

    public List<String> getAllMessages() {
        return messages;
    }

    // BEGIN
    @RabbitListener(queues = "queue")
    public void process(String message) {
        LOGGER.info("Message: " + message);
        messages.add(message);
        // Выполняем какие-то действия
    }
    // END
}
