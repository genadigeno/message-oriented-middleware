package epam.learn.subscriberapplication;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class DurableSubscriber {

    @JmsListener(destination = "sample.topic", containerFactory = "durableFactory", subscription = "durable-subscription")
    public void receiveMessage(Message message) throws JMSException {
        if (message instanceof TextMessage textMessage) {
            System.out.println("Durable Subscriber received: " + textMessage.getText());
        }
    }
}
