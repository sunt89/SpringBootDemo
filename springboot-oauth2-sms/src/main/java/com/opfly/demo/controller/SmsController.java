package com.opfly.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.service.SmsService;

@RestController
@RequestMapping("api/sms")
public class SmsController {
	@Autowired
	private SmsService smsService;
	
	@PostMapping("{phoneNum}")
	public void sendCode(@PathVariable("phoneNum") String phoneNum) {
		smsService.sendCode(phoneNum);
	}
}
