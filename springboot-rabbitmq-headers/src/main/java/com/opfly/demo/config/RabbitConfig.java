package com.opfly.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {
	@Value("${mq.config.queue.info}")
	private String info;	
	@Value("${mq.config.queue.error}")
	private String error;
	@Value("${mq.config.queue.logs}")
	private String logs;
	@Value("${mq.config.exchange}")
	private String exchange;

	/**
	 * 创建info日志消息队列
	 * @return
	 */
	@Bean
	public Queue infoQueue() {
		return new Queue(info);
	}
	
	/**
	 * 创建错误日志消息队列
	 * @return
	 */
	@Bean
	public Queue errorQueue() {
		return new Queue(error);
	}
	
	/**
	 * 创建全日志消息队列
	 * @return
	 */
	@Bean
	public Queue logsQueue() {
		return new Queue(logs);
	}
	
	/**
	 * 创建Headers类型的交换器
	 * @return
	 */
	@Bean
	public HeadersExchange headersExchange() {
		return new HeadersExchange(exchange);
	}
	
	/**
	 * 使用headers绑定交换器和队列
	 * @param infoQueue
	 * @param headersExchange
	 * @return
	 */
	@Bean
	Binding bindingInfoQueue(Queue infoQueue, HeadersExchange headersExchange) {
		Map<String, Object> headerKeys = new HashMap<>();
		headerKeys.put("type", "log");
		headerKeys.put("level", "info");
		return BindingBuilder.bind(infoQueue).to(headersExchange).whereAll(headerKeys).match();
	}
	
	/**
	 * 使用headers绑定交换器和队列
	 * @param errorQueue
	 * @param headersExchange
	 * @return
	 */
	@Bean
	Binding bindingErrorQueue(Queue errorQueue, HeadersExchange headersExchange) {
		Map<String, Object> headerKeys = new HashMap<>();
		headerKeys.put("type", "log");
		headerKeys.put("level", "error");
		return BindingBuilder.bind(errorQueue).to(headersExchange).whereAll(headerKeys).match();
	}
	
	/**
	 * 使用headers绑定交换器和队列
	 * @param logsQueue
	 * @param headersExchange
	 * @return
	 */
	@Bean
	Binding bindingLogsQueue(Queue logsQueue, HeadersExchange headersExchange) {
		Map<String, Object> headerKeys = new HashMap<>();
		headerKeys.put("type", "log");
		return BindingBuilder.bind(logsQueue).to(headersExchange).whereAll(headerKeys).match();
	}
	
	public String getInfo() {
		return info;
	}

	public String getError() {
		return error;
	}
	
	public String getLogs() {
		return logs;
	}

	public String getExchange() {
		return exchange;
	}
}
