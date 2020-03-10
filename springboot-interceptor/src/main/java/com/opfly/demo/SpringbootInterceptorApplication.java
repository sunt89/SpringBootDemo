package com.opfly.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.opfly.demo.interceptor.TestInterceptor;

@SpringBootApplication
public class SpringbootInterceptorApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootInterceptorApplication.class, args);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TestInterceptor()).addPathPatterns("/api/*");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
