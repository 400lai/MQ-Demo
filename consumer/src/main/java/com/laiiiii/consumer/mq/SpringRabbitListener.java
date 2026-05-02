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
}
