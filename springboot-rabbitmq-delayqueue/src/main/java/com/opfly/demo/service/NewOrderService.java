package com.opfly.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.opfly.demo.provider.MessageSender;

@Service
public class NewOrderService {
	@Autowired
	private MessageSender messageSender;
	
	private static int orderId = 0;
	
	@Scheduled(fixedRate = 1000)
    public void createNewOrder() {
		orderId++;
		LocalDateTime localDateTime = LocalDateTime.now();
		messageSender.sendDelay("订单" + orderId + " 产生时间: " + localDateTime);
    }
}
