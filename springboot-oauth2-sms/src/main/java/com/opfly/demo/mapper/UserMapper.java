package com.opfly.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.opfly.demo.pojo.User;

@Mapper
public interface UserMapper {
	@Select(value="select * from user where username=#{username} limit 1")
	public User findByUserName(String username);
	
	@Select(value="select * from user where phonenum=#{phonenum} limit 1")
	public User findByPhoneNum(String phonenum);
}
