package com.laiiiii.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpleAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void testSimpleQueue() {
        // 1.指定队列名称
        String queueName = "simple.queue";
        // 2.准备消息
        String message = "Hello, Spring Amqp!";
        // 3.发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }
}