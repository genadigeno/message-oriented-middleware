package epam.learn.subscriberapplication;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ScaledSubscriber {

    @JmsListener(destination = "Consumer.A.VirtualTopic.sample", containerFactory = "virtualContainerFactory")
    public void receiveMessage(String message) {
        System.out.println("[A] Received: " + message + " | Processed by " + Thread.currentThread().getName());
    }
}
