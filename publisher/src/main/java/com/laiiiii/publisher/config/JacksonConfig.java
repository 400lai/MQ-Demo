package com.laiiiii.publisher.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson 消息转换器配置类
 * 配置 RabbitMQ 使用 Jackson 进行消息的 JSON 序列化与反序列化，
 * 替代默认的 JDK 序列化机制，提高消息的可读性和跨语言兼容性。
 */
@Configuration
public class JacksonConfig {

    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter jjmc = new Jackson2JsonMessageConverter();
        jjmc.setCreateMessageIds(true);
        return jjmc;
    }
}
