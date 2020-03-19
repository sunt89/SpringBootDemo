package com.opfly.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.service.WebSocketService;

@RestController
public class WebSocketController {
	@Autowired
	private WebSocketService webSocketService;
	
	/*@MessageMapping("/send")
	@SendTo("/sub/chat")
	public String sendMsg(String value) {
		System.out.println(value);
		return value;
	}*/
	
	@MessageMapping("/send")
	public void sendMsg(String value) {
		System.out.println(value);
		webSocketService.sendMsg(value);
	}
}
