package com.opfly.demo.consumer;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LogsReceiver {
	@RabbitHandler
	@RabbitListener(queues= {"${mq.config.queue.logs}"})
	public void process(Message message) throws UnsupportedEncodingException {
    	String content = new String(message.getBody());
		System.out.println("Logs Receive: " + content);
	}
}
