package com.opfly.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.opfly.demo.pojo.User;

@Service
public class UserService {
	@Autowired
    private MongoTemplate mongoTemplate;
	
	/**
	 * 添加用户，当用户已经存在时，更新用户
	 * @param user
	 * @return
	 */
	public User saveUser(User user) {
		return mongoTemplate.save(user);
	}
	
	/**
	 * 更新用户
	 * @param user
	 */
	public void updateUser(User user) {
		Query query = new Query(Criteria.where("_id").is(user.getId()));
		Update update = new Update()
				.set("age", user.getAge())
				.set("userName", user.getUserName())
				.set("roles", user.getRoles());
		
		mongoTemplate.updateFirst(query, update, User.class);
	}
	
	/**
	 * 根据用户ID获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return mongoTemplate.findOne(query, User.class);
	}
	
	/**
	 * 根据用户ID删除用户
	 * @param id
	 */
	public void deleteUser(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, User.class);
	}
}
