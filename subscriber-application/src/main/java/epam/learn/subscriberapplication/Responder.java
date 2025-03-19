package epam.learn.subscriberapplication;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

//@Component
public class Responder {

    private final JmsTemplate jmsTemplate;

    public Responder(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "sample2.topic", containerFactory = "durableFactory")
    public void receiveRequest(Message requestMessage) throws JMSException {
        System.out.println("Received message: " + requestMessage.getBody(String.class));
        // Extract the reply-to queue from the incoming request message
        Destination replyQueue = requestMessage.getJMSReplyTo();
        System.out.println("Replying queue: " + replyQueue);

        // Create a response message
        String responseMessage = "Reply to: " + ((TextMessage) requestMessage).getText();

        // Send the response message to the reply queue
        jmsTemplate.send(replyQueue, session -> session.createTextMessage(responseMessage));
        System.out.println("Message sent: " + responseMessage);
    }
}
