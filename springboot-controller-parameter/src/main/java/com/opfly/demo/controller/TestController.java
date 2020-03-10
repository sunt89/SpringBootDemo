package com.opfly.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.pojo.User;

@RestController
@RequestMapping("api/test")
public class TestController {
	@GetMapping("param/noannotation")
	public Map<String, Object> noAnnotation(String name, int age, double score){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("age", age);
		paramMap.put("score", score);
		return paramMap;
	}
	
	@GetMapping("/param/requestparam")
	public Map<String, Object> requestParam(
			@RequestParam("username") String name,
			@RequestParam("user_age") int age, 
			@RequestParam("score") double score) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("age", age);
		paramMap.put("score", score);
		return paramMap;
	}
	
	@GetMapping("/param/requestarray")
	public Map<String, Object> requestArray(String[] names, int[] ages, double[] scores) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", names);
		paramMap.put("age", ages);
		paramMap.put("score", scores);
		return paramMap;
	}
	
	@GetMapping("/param/geturl/{name}/{age}/{score}")
	public Map<String, Object> getUrlParam(
			@PathVariable("name") String name,
			@PathVariable("age") Integer age,
			@PathVariable("score") Double score) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("age", age);
		paramMap.put("score", score);
		return paramMap;
	}
	
	@PostMapping("/param/json")
	public User newUser(@RequestBody User user) {
		return user;
	}
	
	@GetMapping("/param/custom")
	public User newCustomUser(User user) {
		return user;
	}
}
