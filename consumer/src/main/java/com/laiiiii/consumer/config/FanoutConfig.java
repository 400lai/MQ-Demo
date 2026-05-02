package com.laiiiii.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Fanout 交换机配置类
 * 负责初始化交换机、持久化队列及绑定关系
 */
@Configuration
public class FanoutConfig {
    /**
     * 创建 Fanout 类型交换机
     * @return FanoutExchange 交换机实例
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("hmall.fanout").build();
    }

    /**
     * 声明第一个持久化队列
     * @return Queue 队列实例
     */
    @Bean
    public Queue fanoutQueue1() {
        return QueueBuilder.durable("fanout.queue1").build();
    }

    /**
     * 建立第一个队列与 Fanout 交换机的绑定关系
     * @param fanoutExchange 目标 Fanout 交换机
     * @param fanoutQueue1 待绑定的队列
     * @return Binding 绑定实例
     */
    @Bean
    public Binding fanoutBinding1(FanoutExchange fanoutExchange, Queue fanoutQueue1) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Queue fanoutQueue2() {
        return QueueBuilder.durable("fanout.queue2").build();
    }

    @Bean
    public Binding fanoutBinding2(FanoutExchange fanoutExchange, Queue fanoutQueue2) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}
