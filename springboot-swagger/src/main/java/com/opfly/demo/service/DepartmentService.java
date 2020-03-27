package com.opfly.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opfly.demo.mapper.DepartmentMapper;
import com.opfly.demo.pojo.Department;

@Service
public class DepartmentService {
	@Autowired
	DepartmentMapper departmentMapper;
	
	public List<Department> findAll(){
		return departmentMapper.findAll();
	}
	
	public Department getDepartment(int id) {
		return departmentMapper.getDepartment(id);
	}
	
	public Department insertDepartment(Department department) {
		departmentMapper.insertDepartment(department);
		return department;
	}
	
	public int updateDepartment(Department department) {
		return departmentMapper.updateDepartment(department);
	}
	
	public int deleteDepartment(int id) {
		return departmentMapper.deleteDepartment(id);
	}
}
