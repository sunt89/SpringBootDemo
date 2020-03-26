package com.opfly.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
	@Value("${mq.config.exchange}")
	private String exchange;
	@Value("${mq.config.queue.info.routing.key}")
	private String infoRoutingkey;
	@Value("${mq.config.queue.error.routing.key}")
	private String errorRoutingkey;
	
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
	 * 创建Direct类型的交换器
	 * @return
	 */
	@Bean
	public DirectExchange dircetExchange() {
		return new DirectExchange(exchange);
	}
	
	/**
	 * 使用路由键将消息队列与交换器进行绑定
	 * @param infoQueue
	 * @param dircetExchange
	 * @return
	 */
	@Bean
	Binding bindingInfoQueue(Queue infoQueue, DirectExchange dircetExchange) {
		return BindingBuilder.bind(infoQueue).to(dircetExchange).with(infoRoutingkey);
	}
	
	/**
	 * 使用路由键将消息队列与交换器进行绑定
	 * @param errorQueue
	 * @param dircetExchange
	 * @return
	 */
	@Bean
	Binding bindingErrorQueue(Queue errorQueue, DirectExchange dircetExchange) {
		return BindingBuilder.bind(errorQueue).to(dircetExchange).with(errorRoutingkey);
	}
	
	public String getInfo() {
		return info;
	}

	public String getError() {
		return error;
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
}
