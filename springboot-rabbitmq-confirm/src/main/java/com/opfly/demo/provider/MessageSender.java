package com.opfly.demo.provider;

import org.springframework.amqp.rabbit.connection.CorrelationData;
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
	
	public void sendFaking(String msg){
		//使用不存在的路由键向交换机发送消息
		CorrelationData correlationData = new CorrelationData("faking-routing-key");
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "faking.routing.key", msg, correlationData);
		//向不存在的交换器发送消息
		correlationData = new CorrelationData("faking-exchange");
		this.rabbitTemplate.convertAndSend("faking.exchange", rabbitConfig.getErrorRoutingkey(), msg, correlationData);
	}
	
	public void sendInfo(String msg){
		CorrelationData correlationData = new CorrelationData("info-message");
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getInfoRoutingkey(), msg, correlationData);
	}
	
	public void sendError(String msg){
		CorrelationData correlationData = new CorrelationData("error-message");
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), rabbitConfig.getErrorRoutingkey(), msg, correlationData);
	}
}
