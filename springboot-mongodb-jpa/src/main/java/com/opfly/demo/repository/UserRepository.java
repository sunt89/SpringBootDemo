package com.opfly.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.opfly.demo.pojo.User;

public interface UserRepository extends MongoRepository<User, String>{
	/**
	 * 使用JPA规范命名方法
	 * @param userName
	 * @return
	 */
	List<User> findByUserNameLike(String userName);
	
	/**
	 * 自定义查询条件，查询id或者userName相同的一条数据
	 * @param id
	 * @param name
	 * @return
	 */
	@Query("{$or:[{'userName': ?0}, {'_id': ?1}]}")
	List<User> find(String userName, String id);
}
