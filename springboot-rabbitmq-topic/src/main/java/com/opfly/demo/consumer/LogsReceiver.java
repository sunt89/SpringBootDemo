package com.opfly.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues= {"${mq.config.queue.logs}"})
public class LogsReceiver {
	@RabbitHandler
	public void process(String message) {
		System.out.println("Logs Receive: " + message);
	}
}
