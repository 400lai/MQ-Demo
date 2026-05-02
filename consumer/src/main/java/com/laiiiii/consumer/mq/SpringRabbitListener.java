package com.laiiiii.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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

    @RabbitListener(queues = "direct.queue1")
    public void listenDirectQueue1(String message) {
        log.info("消费者1监听到 direct.queue1的消息: {}", message);
    }

    @RabbitListener(queues = "direct.queue2")
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
}
