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
}
