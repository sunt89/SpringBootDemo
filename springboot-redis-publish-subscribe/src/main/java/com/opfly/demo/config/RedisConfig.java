package com.opfly.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.opfly.demo.service.RedisReceiver;

@Configuration
public class RedisConfig {
	@Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
    		MessageListenerAdapter redisListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(redisListenerAdapter, new PatternTopic("testpubsub"));
        return container;
    }
	
	@Bean
    public MessageListenerAdapter userListenerAdapter(RedisReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
	
}
