package epam.learn.pubapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublishController {
    private final MessagePublisher publisher;

    public PublishController(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/{message}")
    public String publish(@PathVariable String message) {
        publisher.sendMessage(message);
        return "Message sent: " + message;
    }
}
