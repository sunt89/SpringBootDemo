package com.opfly.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.service.WebSocketService;

@RestController
public class WebSocketController {
	@Autowired
	private WebSocketService webSocketService;
	
	@MessageMapping("/sendToUser")
	public void sendToUser(Principal principal, String value) {
		String[] userAndMessage = value.split("\\|");
		String sourceUser = principal.getName();
		
		if (userAndMessage.length == 2) {
			webSocketService.sendToUser(userAndMessage[0], sourceUser + ": " + userAndMessage[1]);
		}
		else {
			webSocketService.sendMsg(sourceUser + ": " + value);
		}
	}
}
