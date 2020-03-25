package com.opfly.demo.provider;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opfly.demo.config.RabbitConfig;

@Component
public class MessageSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private RabbitConfig rabbitConfig;
	
	public void sendInfo(String msg){
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getInfoRoutingkey(), msg);
	}
	
	public void sendError(String msg){
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getErrorRoutingkey(), msg);
	}
}
