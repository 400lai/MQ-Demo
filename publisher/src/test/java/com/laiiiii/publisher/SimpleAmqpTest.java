package com.laiiiii.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Slf4j
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

    @Test
    public void testSendObject() {
        // 1.准备消息
        Map<String, Object> msg = new HashMap<>(2);
        msg.put("name", "Tom");
        msg.put("age", 18);
        // 2.发送消息
        rabbitTemplate.convertAndSend("object.queue", msg);
    }

    @Test
    public void testConfirmCallback() {
        // 1.创建CorrelationData
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        // 2.给Future添加ConfirmCallback
        cd.getFuture().thenAccept(
                result -> {
                    if (result.isAck()) {
                        log.debug("发送消息成功，收到 ack!");
                    } else {
                        log.error("发送消息失败，收到 nack, reason : {}", result.getReason());
                    }
                }
        ).exceptionally(
                ex -> {
                    log.error("send message fail", ex);
                    return null;
                }
        );

        // 3.指定交换机名称
        String exchangeName = "hmall.direct";
        // 4.准备消息
        String message = "红色警戒！";
        // 5.发送消息（必须传入 CorrelationData 才能触发 Confirm 回调）
        rabbitTemplate.convertAndSend(exchangeName, "red111", message, cd);
    }

    @Test
    void testSendMessage() {
        // 1.自定义构建消息
        Message message = MessageBuilder
                .withBody("Hello, Spring Amqp!".getBytes())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setContentEncoding("UTF-8")
                .build();
        // 2.发送消息
        for (int i = 0; i < 1000000; i++) {
            rabbitTemplate.send("simple.queue", message);
        }
    }

    @Test
    void testSendDelayMessage() {
        rabbitTemplate.convertAndSend("normal.exchange", "hi", "hello !", message -> {
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
        log.info("延迟消息已发送到 normal.exchange,将在10秒后过期并转入死信队列");
    }
}