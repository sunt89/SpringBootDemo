package com.opfly.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@GetMapping
	public String sendMessage(String message) {
		stringRedisTemplate.convertAndSend("testpubsub", message);
		return "success";
	}
}
