@Configuration
public class RabbitMqConfig {

    // Создаём очередь с именем "queue"
    @Bean
    Queue queue() {
        return new Queue("queue", false);
    }

    // Создаём обменник с именем "exchange"
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    // Подключаем очередь "queue" к обменнику "exchange"
    // Сообщения с ключом "key" будут направлены в очередь "queue"
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("key");
    }
}
