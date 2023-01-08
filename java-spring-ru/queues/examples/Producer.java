@Component
public class Producer {

    // Класс, который даёт простой доступ к брокеру сообщений RabbitMQ
    // Позволяет отправлять и получать сообщения
    @Autowired
    RabbitTemplate rabbitTemplate;

    // Метод, выполняющий отправку сообщений
    public void sendMessage(String message) {

        // Отправляем сообщение в обменник с ключом "key"
        // В зависимости от ключа, сообщение будет отправлено в нужную очередь
        rabbitTemplate.convertAndSend("exchange", "key", message);
    }
}
