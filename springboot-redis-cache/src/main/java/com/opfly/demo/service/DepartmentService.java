package com.opfly.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
	
	@Cacheable(value="redisCache", key="'department_'+#id")
	public Department getDepartment(int id) {
		System.out.println("Call getDepartment");
		return departmentMapper.getDepartment(id);
	}
	
	@CachePut(value="redisCache", key="'department_'+#result.id")
	public Department insertDepartment(Department department) {
		System.out.println("Call insertDepartment");
		departmentMapper.insertDepartment(department);
		return department;
	}
	
	@CachePut(value="redisCache", condition="#result!=null", key="'department_'+#result.id")
	public Department updateDepartment(Department department) {
		System.out.println("Call updateDepartment");
		departmentMapper.updateDepartment(department);
		return departmentMapper.getDepartment(department.getId());
	}
	
	@CacheEvict(value="redisCache", key="'department_'+#id", beforeInvocation=true)
	public int deleteDepartment(int id) {
		System.out.println("Call deleteDepartment");
		return departmentMapper.deleteDepartment(id);
	}
}
