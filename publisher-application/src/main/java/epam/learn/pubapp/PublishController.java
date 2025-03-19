package epam.learn.pubapp;

import jakarta.jms.JMSException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PublishController {
    private final MessagePublisher publisher;

    public PublishController(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/pub-sub/{message}")
    public String publishSubscribe(@PathVariable String message) {
        publisher.sendMessage(message);
        return "Message sent: " + message;
    }

    @GetMapping("/request-reply/{message}")
    public String requestReply(@PathVariable String message) throws JMSException {
        Object response = publisher.sendRequest(message);
        return "Message sent: " + message + "\nResponse: " + response;
    }
}
