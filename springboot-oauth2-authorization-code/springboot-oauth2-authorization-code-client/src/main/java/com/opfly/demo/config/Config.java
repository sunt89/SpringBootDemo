package com.opfly.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.opfly.demo.handler.ThrowErrorHandler;

@Configuration
public class Config {
	@Bean
    public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(); 
		restTemplate.setErrorHandler(new ThrowErrorHandler());
        return restTemplate;
    }
}
