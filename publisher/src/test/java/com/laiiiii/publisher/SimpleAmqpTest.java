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

    @Test
    public void testWorkQueue() {
        // 1.指定队列名称
        String queueName = "work.queue";
        for (int i = 0; i <= 50; i++) {
            // 2.准备消息
            String message = "Hello, Spring Amqp_" + i;
            // 3.发送消息
            rabbitTemplate.convertAndSend(queueName, message);
        }
    }

    @Test
    public void testFanoutQueue() {
        // 1.指定交换机名称
        String exchangeName = "hmall.fanout";
        // 2.准备消息
        String message = "Hello, Spring Amqp!";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    @Test
    public void testDirectQueue() {
        // 1.指定交换机名称
        String exchangeName = "hmall.direct";
        // 2.准备消息
        String message = "红色警戒！";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }
}