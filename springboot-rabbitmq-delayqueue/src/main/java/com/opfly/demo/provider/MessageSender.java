package com.opfly.demo.provider;

import org.springframework.amqp.core.Message;
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
	
	public void sendDelay(String msg){
		/*MessageProperties messageProperties = new MessageProperties();
		messageProperties.setExpiration("5000");
		Message message = new Message(msg.getBytes(), messageProperties);
		this.rabbitTemplate.convertAndSend(rabbitConfig.getOrderDelayExchangeName(), rabbitConfig.getOrderDelayQueueRoutingKey(), message);*/
		this.rabbitTemplate.convertAndSend(rabbitConfig.getOrderDelayExchangeName(), rabbitConfig.getOrderDelayQueueRoutingKey(), msg);
	}
}
