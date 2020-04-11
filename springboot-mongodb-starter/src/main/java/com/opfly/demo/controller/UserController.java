package com.opfly.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.pojo.User;
import com.opfly.demo.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("{id}")
	public User getUser(@PathVariable("id")  String id) {
		return userService.getUser(id);
	}
	
	@PostMapping
	public User addUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@PutMapping
	public void updateUser(@RequestBody User user) {
		userService.updateUser(user);
	}
	
	@DeleteMapping("{id}")
	public void deleteUser(@PathVariable("id") String id) {
		userService.deleteUser(id);
	}
}
