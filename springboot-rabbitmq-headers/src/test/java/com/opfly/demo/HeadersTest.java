package com.opfly.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.opfly.demo.provider.MessageSender;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringbootRabbitmqHeadersApplication.class)
public class HeadersTest {
	@Autowired
	private MessageSender messageSender;
	
	@Test
	public void send() throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			messageSender.sendInfo("[info]" + i);
			messageSender.sendError("[error]" + i);
			messageSender.sendWarn("[warn]" + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
