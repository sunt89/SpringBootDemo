package com.opfly.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestController {
	@RequestMapping("/get")
	public String get() {
		return "success";
	}
}
