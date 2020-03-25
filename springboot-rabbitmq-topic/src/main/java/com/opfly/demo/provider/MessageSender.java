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
	
	public void sendUserLogs(String msg){
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "user.log.info", "[info]" + msg);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "user.log.error", "[error]" + msg);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "user.log.warn", "[warn]" + msg);
	}
	
	public void sendOrderLogs(String msg){
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "order.log.info", "[info]" + msg);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "order.log.error", "[error]" + msg);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "order.log.warn", "[warn]" + msg);
	}
	
	public void sendProductLogs(String msg){
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "product.log.info", "[info]" + msg);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "product.log.error", "[error]" + msg);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "product.log.warn", "[warn]" + msg);
	}
}
