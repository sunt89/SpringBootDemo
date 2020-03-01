package com.opfly.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.opfly.demo.pojo.User;

@Mapper
public interface UserMapper {
	@Select(value="select * from user where username=#{username}")
	public User findByUserName(String username);
}