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
import com.opfly.demo.repository.DepartmentRepository;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@GetMapping("{id}")
	public Department get(@PathVariable int id) {
		return departmentRepository.findById(id).orElse(null);
	}
	
	@PostMapping
	public Department post(@RequestBody Department department) {
		return departmentRepository.save(department);
	}
	
	@PutMapping
	public Department put(@RequestBody Department newDepartment) {
		Department department = departmentRepository.findById(newDepartment.getId()).orElse(null);
		if (department != null) {
			department.setLocation(newDepartment.getLocation());
			department.setName(newDepartment.getName());
			departmentRepository.save(department);
		}
		return department;
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable int id) {
		departmentRepository.deleteById(id);
	}
	
	@GetMapping("findByNameLike")
	public List<Department> findByNameLike(String name) {
		return departmentRepository.findByNameLike(name + "%");
	}
	
	@GetMapping("findByCount")
	public List<Department> findByCount(int count) {
		return departmentRepository.findByCount(count);
	}
}
