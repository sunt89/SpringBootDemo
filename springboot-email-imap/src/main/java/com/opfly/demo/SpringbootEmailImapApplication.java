package com.opfly.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringbootEmailImapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEmailImapApplication.class, args);
	}

}
