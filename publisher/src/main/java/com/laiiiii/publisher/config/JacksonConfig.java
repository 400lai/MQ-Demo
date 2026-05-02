package com.laiiiii.publisher.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson JSON 消息转换器配置类
 */
@Configuration
public class JacksonConfig {
    /**
     * 配置消息转换器为 JSON 格式
     * 用于将 Java 对象序列化为 JSON 格式进行消息传递
     * @return Jackson2JsonMessageConverter 消息转换器实例
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
