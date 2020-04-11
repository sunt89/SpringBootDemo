package com.opfly.demo.controller;

import java.util.List;

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
import com.opfly.demo.repository.UserRepository;

@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("{id}")
	public User getUser(@PathVariable("id")  String id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@GetMapping()
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	@GetMapping("bynamelike")
	public List<User> findByUserNameLike(String userName) {
		return userRepository.findByUserNameLike(userName);
	}
	
	@GetMapping("bynameorid")
	public List<User> findByUserNameOrId(String userName, String id) {
		return userRepository.find(userName, id);
	}
	
	@PostMapping
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping
	public User updateUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@DeleteMapping("{id}")
	public void deleteUser(@PathVariable("id") String id) {
		userRepository.deleteById(id);
	}
}
