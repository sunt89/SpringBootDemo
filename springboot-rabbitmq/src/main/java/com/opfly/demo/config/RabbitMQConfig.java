package com.opfly.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
	public static final String QUEUE_NAME = "testqueue";
	
	//创建队列，如果已经存在该队列，可以省略下面代码
	@Bean
    public Queue testQueue() {
        // 第一个是QUEUE的名字  第二个是消息是否需要持久化处理
        return new Queue(QUEUE_NAME, true);
    }
}
