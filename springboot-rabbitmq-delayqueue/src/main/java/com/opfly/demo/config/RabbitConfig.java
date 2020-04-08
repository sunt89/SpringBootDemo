package com.opfly.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {
	@Value("${mq.config.exchange.order}")
	private String orderExchangeName;	

	@Value("${mq.config.exchange.order.delay}")
	private String orderDelayExchangeName;
	
	@Value("${mq.config.queue.order}")
	private String orderQueueName;
	
	@Value("${mq.config.queue.order.delay}")
	private String orderDelayQueueName;
	
	@Value("${mq.config.queue.order.routing.key}")
	private String orderQueueRoutingKey;
	
	@Value("${mq.config.queue.order.delay.routing.key}")
	private String orderDelayQueueRoutingKey;

	/**
	 * 创建订单消息队列
	 * @return
	 */
	@Bean
	public Queue orderQueue() {
		return new Queue(orderQueueName);
	}
	
	/**
     * 延迟队列配置
     * <p>
     * 1、params.put("x-message-ttl", 5 * 1000);
     * 第一种方式是直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活,（当然二者是兼容的,默认是时间小的优先）
     * 2、rabbitTemplate.convertAndSend(book, message -> {
     * message.getMessageProperties().setExpiration(2 * 1000 + "");
     * return message;
     * });
     * 第二种就是每次发送消息动态设置延迟时间,这样我们可以灵活控制
     **/
	@Bean
	public Queue orderDelayQueue() {
		Map<String, Object> params = new HashMap<>();
		params.put("x-message-ttl", 5 * 1000);
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", orderExchangeName);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", orderQueueRoutingKey);
		return new Queue(orderDelayQueueName, true, false, false, params);
	}
	
	/**
	 * 创建Direct类型的交换器
	 * @return
	 */
	@Bean
	public DirectExchange orderExchange() {
		return new DirectExchange(orderExchangeName);
	}
	
	/**
	 * 创建Direct类型的交换器
	 * @return
	 */
	@Bean
	public DirectExchange orderDelayExchange() {
		return new DirectExchange(orderDelayExchangeName);
	}
	
	/**
	 * 使用路由键将消息队列与交换器进行绑定
	 * @param orderQueue
	 * @param orderExchange
	 * @return
	 */
	@Bean
	Binding bindingOrderQueue(Queue orderQueue, DirectExchange orderExchange) {
		return BindingBuilder.bind(orderQueue).to(orderExchange).with(orderQueueRoutingKey);
	}
	
	/**
	 * 使用路由键将消息队列与交换器进行绑定
	 * @param orderDelayQueue
	 * @param orderDelayExchange
	 * @return
	 */
	@Bean
	Binding bindingOrderDelayQueue(Queue orderDelayQueue, DirectExchange orderDelayExchange) {
		return BindingBuilder.bind(orderDelayQueue).to(orderDelayExchange).with(orderDelayQueueRoutingKey);
	}
	
	
	public String getOrderExchangeName() {
		return orderExchangeName;
	}

	public void setOrderExchangeName(String orderExchangeName) {
		this.orderExchangeName = orderExchangeName;
	}

	public String getOrderDelayExchangeName() {
		return orderDelayExchangeName;
	}

	public void setOrderDelayExchangeName(String orderDelayExchangeName) {
		this.orderDelayExchangeName = orderDelayExchangeName;
	}

	public String getOrderQueueName() {
		return orderQueueName;
	}

	public void setOrderQueueName(String orderQueueName) {
		this.orderQueueName = orderQueueName;
	}

	public String getOrderDelayQueueName() {
		return orderDelayQueueName;
	}

	public void setOrderDelayQueueName(String orderDelayQueueName) {
		this.orderDelayQueueName = orderDelayQueueName;
	}

	public String getOrderQueueRoutingKey() {
		return orderQueueRoutingKey;
	}

	public void setOrderQueueRoutingKey(String orderQueueRoutingKey) {
		this.orderQueueRoutingKey = orderQueueRoutingKey;
	}

	public String getOrderDelayQueueRoutingKey() {
		return orderDelayQueueRoutingKey;
	}

	public void setOrderDelayQueueRoutingKey(String orderDelayQueueRoutingKey) {
		this.orderDelayQueueRoutingKey = orderDelayQueueRoutingKey;
	}
}
