package com.opfly.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate template;
    
    public void sendMsg(String message) {
    	if (message != null && message.trim().length() > 0) {
    		template.convertAndSend("/sub/chat", message);
    	}
    }
    
    public void sendToUser(String user, String message) {
    	if (message != null && message.trim().length() > 0) {
    		template.convertAndSendToUser(user, "/queue/chat", message);
    	}
    }
}
