package com.opfly.demo.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.opfly.demo.util.RandomCode;

@Service
public class SmsService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void sendCode(String phoneNum) {
		String code = RandomCode.random(6, true);
		//此处省略向手机发送短信流程
		System.out.println("手机号：" + phoneNum + "  验证码：" + code);
		//将验证码以<key,value>形式缓存到redis
		stringRedisTemplate.opsForValue().set(phoneNum, code, 3 * 60, TimeUnit.SECONDS);
	}
}
