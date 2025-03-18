package epam.learn.subscriberapplication;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class NonDurableSubscriber {

    @JmsListener(destination = "sample.topic", containerFactory = "nonDurableFactory")
    public void receiveMessage(Message message) throws JMSException {
        if (message instanceof TextMessage textMessage) {
            System.out.println("Non-Durable Subscriber received: " + textMessage.getText());
        }
    }
}
