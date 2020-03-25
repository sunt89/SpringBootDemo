package com.opfly.demo.consumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
* 消息接收者
* @author opfly
* @RabbitListener bindings:绑定队列
* @QueueBinding 
* value:绑定队列的名称
* exchange:配置交换器
* key:配置路由键
*
* @Queue 
* value:配置队列名称
* autoDelete:是否是一个可删除的临时队列
*
* @Exchange 
* value:为交换器起个名称
* type:指定具体的交换器类型
*/

/*@RabbitListener(bindings=@QueueBinding(
		value=@Queue(value="${mq.config.queue.info}", autoDelete="true"),
		exchange=@Exchange(value="${mq.config.exchange}", type=ExchangeTypes.DIRECT),
		key="${mq.config.queue.info.routing.key}"
		))*/
@Component
@RabbitListener(queues= {"${mq.config.queue.info}"})
public class InfoReceiver {
	@RabbitHandler
	public void process(String message) {
		System.out.println("Info Log Receive: " + message);
	}
}
