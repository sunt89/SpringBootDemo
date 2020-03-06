package com.opfly.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.opfly.demo.pojo.Department;

@Mapper
public interface DepartmentMapper {
	public List<Department> findAll();
}
