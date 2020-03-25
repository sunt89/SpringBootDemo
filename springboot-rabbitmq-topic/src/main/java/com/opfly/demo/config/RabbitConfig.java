package com.opfly.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
	@Value("${mq.config.queue.info.routing.key}")
	private String infoRoutingkey;
	@Value("${mq.config.queue.error.routing.key}")
	private String errorRoutingkey;
	@Value("${mq.config.queue.logs.routing.key}")
	private String logsRoutingkey;

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
	 * 创建所有级别日志消息队列
	 * @return
	 */
	@Bean
	public Queue logsQueue() {
		return new Queue(logs);
	}
	
	/**
	 * 创建Topic类型的交换器
	 * @return
	 */
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(exchange);
	}
	
	/**
	 * 使用路由键将消息队列与交换器进行绑定
	 * @param infoQueue
	 * @param topicExchange
	 * @return
	 */
	@Bean
	Binding bindingInfoQueue(Queue infoQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(infoQueue).to(topicExchange).with(infoRoutingkey);
	}
	
	/**
	 * 使用路由键将消息队列与交换器进行绑定
	 * @param errorQueue
	 * @param topicExchange
	 * @return
	 */
	@Bean
	Binding bindingErrorQueue(Queue errorQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(errorQueue).to(topicExchange).with(errorRoutingkey);
	}
	
	/**
	 * 使用路由键将消息队列与交换器进行绑定
	 * @param errorQueue
	 * @param topicExchange
	 * @return
	 */
	@Bean
	Binding bindingLogsQueue(Queue logsQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(logsQueue).to(topicExchange).with(logsRoutingkey);
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

	public String getInfoRoutingkey() {
		return infoRoutingkey;
	}

	public String getErrorRoutingkey() {
		return errorRoutingkey;
	}
	
	public String getLogsRoutingKey() {
		return logsRoutingkey;
	}
}
