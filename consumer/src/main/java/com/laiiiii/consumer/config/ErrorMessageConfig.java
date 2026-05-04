package com.laiiiii.consumer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ErrorMessageConfig {
    private final RabbitTemplate rabbitTemplate;

    @Bean
    public DirectExchange errorExchange() {
        return ExchangeBuilder.directExchange("error.exchange").durable(true).build();
    }

    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable("error.queue").build();
    }

    @Bean
    public Binding errorQueueBinding(Queue errorQueue, DirectExchange errorExchange) {
        return BindingBuilder.bind(errorQueue).to(errorExchange).with("error.routing.key");
    }

    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, "error.exchange", "error.routing.key");
    }
}
