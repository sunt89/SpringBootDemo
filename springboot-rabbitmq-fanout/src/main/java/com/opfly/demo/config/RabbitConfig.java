package com.opfly.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {
	@Value("${mq.config.queue.sms}")
	private String sms;	
	@Value("${mq.config.queue.email}")
	private String email;
	@Value("${mq.config.exchange}")
	private String exchange;

	/**
	 * 短息消息队列
	 * @return
	 */
	@Bean
	public Queue smsQueue() {
		return new Queue(sms);
	}
	
	/**
	 * 邮件消息队列
	 * @return
	 */
	@Bean
	public Queue emailQueue() {
		return new Queue(email);
	}
	
	/**
	 * 创建Fanout类型的交换器
	 * @return
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(exchange);
	}
	
	/**
	 * 消息队列与交换器进行绑定，Fanout类型的交换器不需要提供路由键
	 * @param smsQueue
	 * @param fanoutExchange
	 * @return
	 */
	@Bean
	Binding bindingSMSQueue(Queue smsQueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(smsQueue).to(fanoutExchange);
	}
	
	/**
	 * 消息队列与交换器进行绑定，Fanout类型的交换器不需要提供路由键
	 * @param emailQueue
	 * @param fanoutExchange
	 * @return
	 */
	@Bean
	Binding bindingEmailQueue(Queue emailQueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(emailQueue).to(fanoutExchange);
	}
	
	public String getExchange() {
		return exchange;
	}
	
	public String getSms() {
		return sms;
	}
	
	public String getEmail() {
		return email;
	}
}
