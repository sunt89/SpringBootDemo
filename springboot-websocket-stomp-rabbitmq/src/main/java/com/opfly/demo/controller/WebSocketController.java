package com.opfly.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

	@MessageMapping("/exchange")
	//消息将使用 **new.test** 作为路由键发送消息到名为 **opfly.chat.topic** 的交换器。
	@SendTo("/exchange/opfly.chat.topic/news.test")
	public String exchange(String value) {
		return value;
	}
	
	@MessageMapping("/queue")
	@SendTo("/queue/log.info")
	public String queue(String value) {
		return value;
	}
	
	@MessageMapping("/amq-queue")
	@SendTo("/amq/queue/log.error")
	public String amqqueue(String value) {
		return value;
	}
	
	@MessageMapping("/topic")
	@SendTo("/topic/log.warn")
	public String topic(String value) {
		return value;
	}
}
