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
	
	@GetMapping("{id}")
	public Department get(@PathVariable("id") int id) {
		return departmentService.getDepartment(id);
	}
	
	@PostMapping
	public Department insert(@RequestBody Department department) {
		return departmentService.insertDepartment(department);
	}
	
	@PutMapping
	public Department update(@RequestBody Department department) {
		return departmentService.updateDepartment(department);
	}
	
	@DeleteMapping("{id}")
	public int delete(@PathVariable("id") int id) {
		return departmentService.deleteDepartment(id);
	}
}
