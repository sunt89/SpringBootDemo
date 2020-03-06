package com.opfly.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.pojo.Department;
import com.opfly.demo.service.DepartmentService;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	@GetMapping
	public List<Department> get(){
		return departmentService.findAll();
	}
}
