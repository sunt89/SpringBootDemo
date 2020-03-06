package com.opfly.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.pojo.User;

@RestController
@RequestMapping("api/test")
public class TestController {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	
	@GetMapping("stringRedisTemplate")
	public String stringRedisTemplate() {
		stringRedisTemplate.opsForValue().set("strkey01", "teststr");
		System.out.println(stringRedisTemplate.opsForValue().get("strkey01"));
		
		stringRedisTemplate.opsForHash().put("hashkey01", "id", "1");
		stringRedisTemplate.opsForHash().put("hashkey01", "name", "TOM");
		stringRedisTemplate.opsForHash().put("hashkey01", "age", "18");
		stringRedisTemplate.opsForHash().put("hashkey01", "sex", "1");
		
		System.out.println(stringRedisTemplate.opsForHash().get("hashkey01", "id"));
		System.out.println(stringRedisTemplate.opsForHash().get("hashkey01", "name"));
		System.out.println(stringRedisTemplate.opsForHash().get("hashkey01", "age"));
		System.out.println(stringRedisTemplate.opsForHash().get("hashkey01", "sex"));
		return "success";
	}
	
	@GetMapping("redisTemplate")
	public String redisTemplate() {
		//使用redisTemplate写入字符串
		redisTemplate.opsForValue().set("strkey02", "teststr2");
		System.out.println(redisTemplate.opsForValue().get("strkey02"));
		
		User newUser = new User();
		newUser.setId(1);
		newUser.setName("Jerry");
		newUser.setAge(20);
		newUser.setSex(0);
		//将用户对象写入Redis
		redisTemplate.opsForValue().set("user:1", newUser);
		
		//读取用户对象
		User tempuser = (User)redisTemplate.opsForValue().get("user:1");
		System.out.println(tempuser.getId());
		System.out.println(tempuser.getName());
		System.out.println(tempuser.getAge());
		System.out.println(tempuser.getSex());
		return "success";
	}
}
