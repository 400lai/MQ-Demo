package com.laiiiii.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SpringRabbitListener {
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String message) {
        log.info("监听到simple.queue的消息: {}", message);
    }
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue1(String message) {
        log.info("消费者1接收到消息: {}", message);
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2(String message) {
        log.info("【消费者2】接收到消息: {}", message);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String message) {
        log.info("消费者1监听到 fanout.queue1的消息: {}", message);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String message) {
        log.info("消费者2监听到 fanout.queue2的消息: {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1", durable = "true"),
            exchange = @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String message) {
        log.info("消费者1监听到 direct.queue1的消息: {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String message) {
        log.info("消费者2监听到 direct.queue2的消息: {}", message);
    }

    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String message) {
        log.info("消费者1监听到 topic.queue1的消息: {}", message);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String message) {
        log.info("消费者2监听到 topic.queue2的消息: {}", message);
    }

    @RabbitListener(queues = "object.queue")
    public void listenObjectQueue(Map<String, Object> message) {
        log.info("消费者监听到 object.queue的消息: {}", message);
    }
}
