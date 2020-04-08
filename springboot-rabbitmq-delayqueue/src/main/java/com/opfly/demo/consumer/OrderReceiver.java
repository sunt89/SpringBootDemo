package com.opfly.demo.consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = {"${mq.config.queue.order}"})
@Component
public class OrderReceiver {
	@RabbitHandler
	public void process(String message) {
		System.out.println(LocalDateTime.now() + " Order Receive: " + message);
	}
	/*@RabbitListener(queues = {"${mq.config.queue.order}"})
	@RabbitHandler
	public void process(Message message) {
		System.out.println(LocalDateTime.now() + " Order Receive: " + new String(message.getBody()));
	}*/
}
