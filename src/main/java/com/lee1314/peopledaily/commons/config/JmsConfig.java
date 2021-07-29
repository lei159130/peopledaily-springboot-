package com.lee1314.peopledaily.commons.config;

import com.lee1314.peopledaily.commons.properties.JmsProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;


/**
 * @author leili
 */
@Configuration
public class JmsConfig {

    private final JmsProperties jmsProperties;

    @Autowired
    public JmsConfig(JmsProperties jmsProperties) {
        this.jmsProperties = jmsProperties;
    }

    @Bean(name = "reptile-queue")
    public Queue reptileQueue() {
        return new ActiveMQQueue(jmsProperties.getReptileQueue());
    }

    @Bean(name = "save-queue")
    public Queue saveQueue() {
        return new ActiveMQQueue(jmsProperties.getSaveQueue());
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(jmsProperties.getUserName(), jmsProperties.getPassword(), jmsProperties.getBrokerUrl());
    }

    @Bean
    public JmsMessagingTemplate jmsMessageTemplate() {
        return new JmsMessagingTemplate(connectionFactory());
    }

    @Bean("queueListener")
    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
