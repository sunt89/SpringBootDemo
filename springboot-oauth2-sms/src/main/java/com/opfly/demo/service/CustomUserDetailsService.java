package com.opfly.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.opfly.demo.mapper.UserMapper;
import com.opfly.demo.pojo.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
	
	public UserDetails loadUserByPhoneNum(String phoneNum) throws UsernameNotFoundException {
		User user = userMapper.findByPhoneNum(phoneNum);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
}
