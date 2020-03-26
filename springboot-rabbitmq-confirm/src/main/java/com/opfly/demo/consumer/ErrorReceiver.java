package com.opfly.demo.consumer;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

//配置ackMode为MANUAL，进行手动确认消息
@RabbitListener(queues = {"${mq.config.queue.error}"}, ackMode = "MANUAL")
@Component
public class ErrorReceiver {
	@RabbitHandler
	public void process(String content, Channel channel, Message message) throws IOException {
		try {
			throw new Exception();
		} catch (Exception e) {
			//当发生异常时，我们可以使用channle.basicNack方法把消息重新加入到队列，重新进行消费，但是这种情况容易发生死循环，
			//或者在发生异常时可以将requeue设置为false，将消息丢弃
			//channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			//channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		}
	}
}
