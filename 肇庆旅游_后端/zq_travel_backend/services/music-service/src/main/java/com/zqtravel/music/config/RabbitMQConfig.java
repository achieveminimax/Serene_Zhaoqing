package com.zqtravel.music.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 音乐播放记录交换机
     */
    @Bean
    public TopicExchange musicExchange() {
        return new TopicExchange("music.exchange", true, false);
    }

    /**
     * 音乐播放记录队列
     */
    @Bean
    public Queue musicPlayQueue() {
        return new Queue("music.play.queue", true, false, false);
    }

    /**
     * 绑定播放记录队列到交换机
     */
    @Bean
    public Binding musicPlayBinding() {
        return BindingBuilder.bind(musicPlayQueue())
                .to(musicExchange())
                .with("music.play");
    }

    /**
     * 用户播放历史队列
     */
    @Bean
    public Queue userPlayHistoryQueue() {
        return new Queue("user.play.history.queue", true, false, false);
    }

    /**
     * 绑定用户播放历史队列到交换机
     */
    @Bean
    public Binding userPlayHistoryBinding() {
        return BindingBuilder.bind(userPlayHistoryQueue())
                .to(musicExchange())
                .with("user.play.history");
    }
}