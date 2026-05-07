package com.zqtravel.shop.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 交换机
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String INVENTORY_EXCHANGE = "inventory.exchange";

    // 队列
    public static final String ORDER_QUEUE = "order.queue";
    public static final String INVENTORY_QUEUE = "inventory.queue";
    public static final String NOTIFICATION_QUEUE = "notification.queue";

    // 路由键
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";
    public static final String ORDER_PAID_ROUTING_KEY = "order.paid";
    public static final String ORDER_CANCELLED_ROUTING_KEY = "order.cancelled";
    public static final String INVENTORY_DEDUCTED_ROUTING_KEY = "inventory.deducted";
    public static final String INVENTORY_RESTORED_ROUTING_KEY = "inventory.restored";

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange inventoryExchange() {
        return new DirectExchange(INVENTORY_EXCHANGE, true, false);
    }

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", ORDER_QUEUE + ".dlq")
                .build();
    }

    @Bean
    public Queue inventoryQueue() {
        return QueueBuilder.durable(INVENTORY_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", INVENTORY_QUEUE + ".dlq")
                .build();
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", NOTIFICATION_QUEUE + ".dlq")
                .build();
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder.bind(orderQueue())
                .to(orderExchange())
                .with(ORDER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding orderPaidBinding() {
        return BindingBuilder.bind(orderQueue())
                .to(orderExchange())
                .with(ORDER_PAID_ROUTING_KEY);
    }

    @Bean
    public Binding orderCancelledBinding() {
        return BindingBuilder.bind(orderQueue())
                .to(orderExchange())
                .with(ORDER_CANCELLED_ROUTING_KEY);
    }

    @Bean
    public Binding inventoryDeductedBinding() {
        return BindingBuilder.bind(inventoryQueue())
                .to(inventoryExchange())
                .with(INVENTORY_DEDUCTED_ROUTING_KEY);
    }

    @Bean
    public Binding inventoryRestoredBinding() {
        return BindingBuilder.bind(inventoryQueue())
                .to(inventoryExchange())
                .with(INVENTORY_RESTORED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setMandatory(true);
        // 确认回调
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                // 消息发送失败处理
                System.err.println("消息发送失败: " + cause);
            }
        });
        // 返回回调
        template.setReturnsCallback(returned -> {
            System.err.println("消息被退回: " + returned.getMessage() + ", replyCode: " + returned.getReplyCode() + ", replyText: " + returned.getReplyText());
        });
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(5);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}