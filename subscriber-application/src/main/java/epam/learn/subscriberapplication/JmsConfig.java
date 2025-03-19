package epam.learn.subscriberapplication;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String brokerUsername;

    @Value("${spring.activemq.password}")
    private String brokerPassword;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        factory.setUserName(brokerUsername);
        factory.setPassword(brokerPassword);
        factory.setClientID("local-client");
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(activeMQConnectionFactory());
    }

    @Bean
    public DefaultJmsListenerContainerFactory durableFactory(CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory nonDurableFactory(CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
        return new JmsTemplate(cachingConnectionFactory);
    }
}
