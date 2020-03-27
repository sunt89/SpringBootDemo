package com.opfly.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（controller）
 *开始支持@MessageMapping,就像是使用@requestMapping一样。
 * @author Dell
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

 @Override
 public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
     //注册一个Stomp的端点,并指定使用SockJS协议。
     stompEndpointRegistry.addEndpoint("/socket").withSockJS();
 }

 @Override
 public void configureMessageBroker(MessageBrokerRegistry registry) {
     //客户端订阅destination类型
	 registry
	 .enableStompBrokerRelay("/exchange", "/topic", "/queue", "/amq/queue", "/temp-queue")
	 .setRelayHost("192.168.1.125")
	 .setRelayPort(61613)
	 .setClientLogin("rabbitadmin")
	 .setClientPasscode("xxxxxxx")
	 .setSystemLogin("rabbitadmin")
	 .setSystemPasscode("xxxxxxx");
     //服务端点请求前缀
     registry.setApplicationDestinationPrefixes("/request");
 }
}
