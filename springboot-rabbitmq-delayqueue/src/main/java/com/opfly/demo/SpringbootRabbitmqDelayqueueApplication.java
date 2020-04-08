package com.opfly.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringbootRabbitmqDelayqueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRabbitmqDelayqueueApplication.class, args);
	}

}
