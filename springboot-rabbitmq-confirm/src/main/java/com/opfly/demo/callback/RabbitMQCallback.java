package com.opfly.demo.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQCallback implements ConfirmCallback, ReturnCallback{

	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		//true：若交换机无法找到消息对应的队列，将会调用basic.return将消息返回给生产者。
		//false：消息直接被丢弃
		template.setMandatory(true);
		template.setConfirmCallback(this);
		template.setReturnCallback(this);
		return template;
	};
	
	//确认消息是否被接收
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			System.out.println("消息：" + correlationData.getId() + "发送成功");
		}
		else {
			System.out.println("消息：" + correlationData.getId() + "发送失败，失败原因: " + cause);
		}
	}
	
	//消息被回退
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println("【message】：" + message);
        System.out.println("【replyCode】：" + replyCode);
        System.out.println("【replyText】：" + replyText);
        System.out.println("【exchange】：" + exchange);
        System.out.println("【routingKey】：" + routingKey);
	}

}
