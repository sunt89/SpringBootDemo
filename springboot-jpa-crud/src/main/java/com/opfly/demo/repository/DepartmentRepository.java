package com.opfly.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.opfly.demo.pojo.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer>{
	List<Department> findByNameLike(String name);
	
	@Query(value = "select * from dept limit ?1", nativeQuery =true)
	List<Department> findByCount(int count);
}
