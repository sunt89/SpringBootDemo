package com.opfly.demo.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role {
	private Long id;
	private String role;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
