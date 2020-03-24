package com.opfly.demo.provider;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.opfly.demo.config.RabbitMQConfig;

@Component
public class MessageSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void send(String msg) {
		if (!StringUtils.isEmpty(msg)){
			rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, msg);
		}
	}
}
