package com.opfly.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues= {"${mq.config.queue.info}"})
public class InfoReceiver {
	@RabbitHandler
	public void process(String message) {
		System.out.println("Info Log Receive: " + message);
	}
}
