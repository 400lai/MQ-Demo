# MQ-Demo 消息队列演示项目

## 项目简介

这是一个基于 Spring Boot + RabbitMQ 的消息队列演示项目，展示了消息的发布与消费的基本用法。

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
│   ├── src/main/java
│   └── src/main/resources
├── consumer           # 消息消费者模块
│   ├── src/main/java
│   └── src/main/resources
└── pom.xml           # 父工程POM配置
```

## 模块说明

### publisher
消息发布者模块，负责向 RabbitMQ 发送消息。

### consumer
消息消费者模块，负责从 RabbitMQ 接收并处理消息。

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

public void sendMessage(String exchange, String routingKey, Object message) {
    rabbitTemplate.convertAndSend(exchange, routingKey, message);
}
```

### 接收消息

在 consumer 模块中使用 `@RabbitListener` 注解监听消息：

```java
@RabbitListener(queues = "queue.name")
public void handleMessage(String message) {
    // 处理消息
}
```

## 许可证

本项目仅供学习参考使用。
