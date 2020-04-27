package com.opfly.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {
	@GetMapping
	public String get(@RequestAttribute("userName") String userName) {
		System.out.println("call rest api");
		System.out.println("用户名：" + userName);
		return "success";
	}
}
