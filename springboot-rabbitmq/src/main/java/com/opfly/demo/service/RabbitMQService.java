package com.opfly.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.opfly.demo.config.RabbitMQConfig;
import com.opfly.demo.pojo.User;

@Service
public class RabbitMQService {
	@RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME})
	public void messageListerner(String message) {
		System.out.println("Receive " + message);
	}
	
	@RabbitListener(queues = {RabbitMQConfig.OBJ_QUEUE_NAME})
	public void messageListerner2(User user) {
		System.out.println("name " + user.getName());
		System.out.println("age " + user.getAge());
		System.out.println("email " + user.getEmail());
	}
}
