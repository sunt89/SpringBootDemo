package com.opfly.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.opfly.demo.provider.MessageSender;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringbootRabbitmqFanoutApplication.class)
public class FanoutTest {
	@Autowired
	private MessageSender messageSender;
	
	@Test
	public void send() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			messageSender.send("message " + i);
			Thread.sleep(1000);
		}
	}
}
