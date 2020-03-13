package com.opfly.demo.controller;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {
	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping("sendemail")
	public void sendEmail(){
		try{
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		    final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		    message.setFrom("sun_t89@126.com");
		    //这里修改成目的邮箱地址
		    message.setTo("xxxx@test.com");
		    message.setSubject("测试邮件主题");
		    message.setText("测试邮件内容");
		    mailSender.send(mimeMessage);
		}
		catch(Exception ex){
		}
	}
}
