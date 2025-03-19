package epam.learn.pubapp;

import jakarta.jms.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MessagePublisher {
    private final JmsTemplate jmsTemplate;

    public MessagePublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    // for pub/sub pattern - Task 1
    public void sendMessage(String message) {
        jmsTemplate.convertAndSend("sample.topic", message);
        System.out.println("Published: " + message);
    }

    //for request-reply pattern - Task 2
    public Object sendRequest(String requestMessage) throws JMSException {
        // Create a temporary reply queue
        try (Connection connection = jmsTemplate.getConnectionFactory().createConnection()){
            Destination replyQueue = connection.createSession(false, Session.AUTO_ACKNOWLEDGE).createTemporaryQueue();
            // Send the request message and set the reply-to destination to the temporary queue
            jmsTemplate.send("sample2.topic", session -> {
                Message message = session.createTextMessage(requestMessage);
                message.setJMSReplyTo(replyQueue);
                return message;
            });
            // Wait for the response on the reply queue
            System.out.println("message send and waiting for response...");
            Object response = jmsTemplate.receiveAndConvert(replyQueue);
            System.out.println("response received: " + response);
            return response;
        }
    }

    private final AtomicInteger counter = new AtomicInteger(0);
    public void sendMessageToVirtual(String message) {
        jmsTemplate.convertAndSend("VirtualTopic.sample", counter.incrementAndGet()+"-"+message);
        System.out.println("Message sent: " + message);
    }
}