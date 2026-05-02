package com.laiiiii.publisher.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MqConfig {
    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.error("监听到了消息return callback");
            log.error("exchange: {}", returnedMessage.getExchange());
            log.error("routingKey: {}", returnedMessage.getRoutingKey());
            log.error("message: {}", new String(returnedMessage.getMessage().getBody()));
            log.error("replyCode: {}", returnedMessage.getReplyCode());
            log.error("replyText: {}", returnedMessage.getReplyText());
        });
    }
}
