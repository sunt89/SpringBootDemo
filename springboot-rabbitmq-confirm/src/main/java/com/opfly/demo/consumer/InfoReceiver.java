package com.opfly.demo.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
//配置ackMode为MANUAL，进行手动确认消息
@RabbitListener(queues= {"${mq.config.queue.info}"}, ackMode = "MANUAL")
public class InfoReceiver {
	@RabbitHandler
	public void process(String content, Channel channel, Message message) {
		System.out.println("Info Log Receive: " + content);
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
