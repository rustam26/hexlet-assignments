package exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    QueueListener listener;

    @GetMapping(path = "")
    public List<String> getAllMessages() {
        return listener.getAllMessages();
    }
}
