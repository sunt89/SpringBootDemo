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
	
	public void send(String msg){
		//fanout交换器不处理路由键，所以此处将路由键设置为空字符串即可
		this.rabbitTemplate.convertAndSend(rabbitConfig.getExchange(), "", msg);
	}
}
