package com.opfly.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@GetMapping("pipeline")
	public String testPipeline(){
		Long start = System.currentTimeMillis();
		List<Object> results = stringRedisTemplate.executePipelined(
			new RedisCallback<Object>() {
				@Override
			    public Object doInRedis(RedisConnection connection) throws DataAccessException {
			        StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
			        for(int i=0; i< 100000; i++) {
			        	stringRedisConn.set("key"+ i, String.valueOf(i));
			        }
			        return null;
				}
			});
		Long end = System.currentTimeMillis();
		String result = "耗时：" + (end - start) + " 毫秒";
		return result;
	}
	
	@GetMapping("nopipeline")
	public String testNoPipeline(){
		Long start = System.currentTimeMillis();
		for(int i=0; i< 100000; i++) {
        	stringRedisTemplate.opsForValue().set("key"+ i, String.valueOf(i));
        }
		Long end = System.currentTimeMillis();
		String result = "耗时：" + (end - start) + " 毫秒";
		return result;
	}
}
