package com.opfly.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@GetMapping
	public List<Object> testMulti(){
		List<Object> txResults = stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				//redis 乐观锁，观察被监测的字段是否在执行exec命令之前发生变化
				operations.watch("count");
			    operations.multi();
			    operations.opsForValue().increment("key01");
			    operations.opsForValue().increment("key02");
			    operations.opsForValue().increment("key03");
			    //在执行exec命令前，操作命令都只是被加入到队列中，没有真正的执行，此处count值未发生变动
			    operations.opsForValue().increment("count");
			    //执行exec命令，将先判断count是否在监控后被修改过，如果是则不执行事务，否则将执行事务
			    return operations.exec();
			  }
			});
		return txResults;
	}
}
