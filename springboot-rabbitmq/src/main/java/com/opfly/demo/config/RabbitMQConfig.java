package com.opfly.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
	public static final String QUEUE_NAME = "testqueue";
	public static final String OBJ_QUEUE_NAME = "testobjqueue";
	
	//创建队列，如果已经存在该队列，可以省略下面代码
	@Bean
    public Queue testQueue() {
        // 第一个是QUEUE的名字  第二个是消息是否需要持久化处理
        return new Queue(QUEUE_NAME, true);
    }
	
	@Bean
    public Queue testObjQueue() {
        // 第一个是QUEUE的名字  第二个是消息是否需要持久化处理
        return new Queue(OBJ_QUEUE_NAME, true);
    }
	
	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		// 指定序列化方式为jackson
        template.setMessageConverter(new Jackson2JsonMessageConverter());
		return template;
	};
	
	@Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 指定反序列化方式为jackson
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
