package com.opfly.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = {"${mq.config.queue.error}"})
@Component
public class ErrorReceiver {
	@RabbitHandler
	public void process(String message) {
		System.out.println("Error Log Receive: " + message);
	}
}
