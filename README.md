# MQ-Demo 消息队列演示项目

## 项目简介

这是一个基于 Spring Boot + RabbitMQ 的消息队列演示项目，展示了多种 RabbitMQ 消息模型的实现方式，包括 Simple Queue、Work Queue、Fanout Exchange、Direct Exchange 和 Topic Exchange。

## 技术栈

- Java 8
- Spring Boot 2.7.12
- RabbitMQ
- Lombok
- Maven

## 项目结构

```
mq-demo
├── publisher          # 消息发布者模块
│   ├── src/main/java/com/laiiiii/publisher
│   │   ├── config
│   │   │   └── JacksonConfig.java   # JSON 消息转换器配置
│   │   └── PublisherApplication.java
│   └── src/main/resources
├── consumer           # 消息消费者模块
│   ├── src/main/java/com/laiiiii/consumer
│   │   ├── config
│   │   │   ├── FanoutConfig.java    # Fanout 交换机配置
│   │   │   └── JacksonConfig.java   # JSON 消息转换器配置
│   │   ├── mq
│   │   │   └── SpringRabbitListener.java  # 消息监听器
│   │   └── ConsumerApplication.java
│   └── src/main/resources
└── pom.xml           # 父工程POM配置
```

## 模块说明

### publisher
消息发布者模块，负责向 RabbitMQ 发送消息。配置了 Jackson2JsonMessageConverter 用于对象与 JSON 的转换。

### consumer
消息消费者模块，负责从 RabbitMQ 接收并处理消息。包含了多种 RabbitMQ 消息模型的实现。

## 消息模型

### 1. Simple Queue
- 队列: `simple.queue`
- 单个生产者 -> 单个消费者

### 2. Work Queue
- 队列: `work.queue`
- 单个生产者 -> 多个消费者（轮询消费）

### 3. Fanout Exchange
- 交换机: `hmall.fanout`
- 队列: `fanout.queue1`, `fanout.queue2`
- 广播模式，消息发送到所有绑定的队列

### 4. Direct Exchange
- 交换机: `hmall.direct`
- 队列: `direct.queue1` (routing key: red, blue), `direct.queue2` (routing key: red, yellow)
- 基于 routing key 精确匹配

### 5. Topic Exchange
- 队列: `topic.queue1`, `topic.queue2`
- 支持通配符匹配（* 匹配一个词，# 匹配零或多个词）

### 6. Object Queue
- 队列: `object.queue`
- 支持发送和接收 Java 对象（JSON 序列化）

## 环境要求

- JDK 8+
- Maven 3.6+
- RabbitMQ 服务器

## 配置说明

### RabbitMQ 配置

在 `consumer/src/main/resources/application.yml` 中配置 RabbitMQ 连接信息：

```yaml
spring:
  rabbitmq:
    host: 192.168.100.130  # RabbitMQ 服务器地址
    port: 5672             # 端口号
    virtual-host: /hmall   # 虚拟主机
    username: hmall        # 用户名
    password: 123          # 密码
```

### 消息转换器

项目使用 Jackson2JsonMessageConverter 实现对象与 JSON 的自动转换，配置在两个模块的 `JacksonConfig` 中。

## 快速开始

### 1. 启动 RabbitMQ

确保 RabbitMQ 服务器已启动并可访问。

### 2. 编译项目

```bash
mvn clean install
```

### 3. 启动消费者

```bash
cd consumer
mvn spring-boot:run
```

### 4. 启动发布者

```bash
cd publisher
mvn spring-boot:run
```

## 开发指南

### 发送消息

在 publisher 模块中注入 `RabbitTemplate` 发送消息：

```java
@Autowired
private RabbitTemplate rabbitTemplate;

// 发送简单消息
rabbitTemplate.convertAndSend("simple.queue", "Hello World");

// 发送对象消息
rabbitTemplate.convertAndSend("object.queue", map);
```

### 接收消息

在 consumer 模块中使用 `@RabbitListener` 注解监听消息：

```java
@RabbitListener(queues = "queue.name")
public void handleMessage(String message) {
    // 处理消息
}

// 使用注解方式声明队列、交换机和绑定
@RabbitListener(bindings = @QueueBinding(
    value = @Queue(name = "queue.name", durable = "true"),
    exchange = @Exchange(name = "exchange.name", type = ExchangeTypes.DIRECT),
    key = {"routing.key"}
))
public void handleDirectMessage(String message) {
    // 处理消息
}
```

## 许可证

本项目仅供学习参考使用。
