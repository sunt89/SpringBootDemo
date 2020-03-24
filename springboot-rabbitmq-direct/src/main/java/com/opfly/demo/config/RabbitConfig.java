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
	@Value("${mq.config.queue.news}")
	private String news;	
	@Value("${mq.config.queue.ads}")
	private String ads;
	@Value("${mq.config.exchange}")
	private String exchange;
	@Value("${mq.config.queue.news.routing.key}")
	private String newsRoutingkey;
	@Value("${mq.config.queue.ads.routing.key}")
	private String adsRoutingkey;

	/**
	 * 创建新闻消息队列
	 * @return
	 */
	@Bean
	public Queue newsQueue() {
		return new Queue(news);
	}
	
	/**
	 * 创建广告消息队列
	 * @return
	 */
	@Bean
	public Queue adsQueue() {
		return new Queue(ads);
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
	 * @param newsQueue
	 * @param dircetExchange
	 * @return
	 */
	@Bean
	Binding bindingNewsQueue(Queue newsQueue, DirectExchange dircetExchange) {
		return BindingBuilder.bind(newsQueue).to(dircetExchange).with(newsRoutingkey);
	}
	
	/**
	 * 使用路由键将消息队列与交换器进行绑定
	 * @param adsQueue
	 * @param dircetExchange
	 * @return
	 */
	@Bean
	Binding bindingAdsQueue(Queue adsQueue, DirectExchange dircetExchange) {
		return BindingBuilder.bind(adsQueue).to(dircetExchange).with(adsRoutingkey);
	}
	
	public String getNews() {
		return news;
	}

	public void setAds(String ads) {
		this.ads = ads;
	}

	public String getExchange() {
		return exchange;
	}
	
	public String getNewsRoutingkey() {
		return newsRoutingkey;
	}

	public String getAdsRoutingkey() {
		return adsRoutingkey;
	}
}
