package com.opfly.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.opfly.demo.config.RabbitMQConfig;

@Service
public class RabbitMQService {
	@RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME})
	public void messageListerner(String message) {
		System.out.println("Receive " + message);
	}
}
