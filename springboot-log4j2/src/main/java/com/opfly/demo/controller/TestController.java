package com.opfly.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("api/log")
@Log4j2
public class TestController {
	//private static final Logger log = LogManager.getLogger(TestController.class);
	
	@GetMapping
	public String get() {
		log.debug("this is a debug log");
		log.info("this is a info log");
		log.warn("this is a warn log");
		log.error("this is a error log");
		log.fatal("this is a fatal log");
		return "success";
	}
}
