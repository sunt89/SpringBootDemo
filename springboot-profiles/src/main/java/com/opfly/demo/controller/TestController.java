package com.opfly.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.config.CustomProperties;

@RestController
@RequestMapping("api/test")
public class TestController {
	@Autowired
	private CustomProperties customProperties;
	@GetMapping
	public CustomProperties get() {
		return customProperties;
	}
}
