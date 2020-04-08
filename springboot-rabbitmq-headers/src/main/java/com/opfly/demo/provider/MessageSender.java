package com.opfly.demo.provider;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
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
		MessageProperties messageProperties = new MessageProperties();
		// 设置消息是否持久化。Persistent表示持久化，Non-persistent表示不持久化
		messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		messageProperties.setContentType("text/plain");
		messageProperties.setHeader("type", "log");
		messageProperties.setHeader("level", "info");
		Message message = new Message(msg.getBytes(), messageProperties);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), null, message);
	}
	
	public void sendError(String msg){
		MessageProperties messageProperties = new MessageProperties();
		// 设置消息是否持久化。Persistent表示持久化，Non-persistent表示不持久化
		messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		messageProperties.setContentType("text/plain");
		messageProperties.setHeader("type", "log");
		messageProperties.setHeader("level", "error");
		Message message = new Message(msg.getBytes(), messageProperties);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), null, message);
	}
	
	public void sendWarn(String msg){
		MessageProperties messageProperties = new MessageProperties();
		// 设置消息是否持久化。Persistent表示持久化，Non-persistent表示不持久化
		messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		messageProperties.setContentType("text/plain");
		messageProperties.setHeader("type", "log");
		messageProperties.setHeader("level", "warn");
		Message message = new Message(msg.getBytes(), messageProperties);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), null, message);
	}
}
