package com.opfly.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = {"${mq.config.queue.sms}"})
@Component
public class SmsReceiver {
	@RabbitHandler
	public void process(String message) {
		System.out.println("SMS Receive: " + message);
	}
}
