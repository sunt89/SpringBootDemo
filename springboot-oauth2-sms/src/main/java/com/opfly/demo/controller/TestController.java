package com.opfly.demo.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestController {
	@GetMapping("/get")
	public String get(Principal principal) {
		return "success " + principal.getName();
	}
}
