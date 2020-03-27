package com.opfly.demo.pojo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "部门模型")
public class Department implements Serializable{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "部门ID")
	private int id;
	@ApiModelProperty(value = "部门名称")
	private String name;
	@ApiModelProperty(value = "部门所在城市")
	private String location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
