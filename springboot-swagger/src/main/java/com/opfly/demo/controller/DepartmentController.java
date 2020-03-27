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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "部门管理相关接口")
@RequestMapping("api/department")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	@GetMapping
	@ApiOperation("获取所有部门信息")
	public List<Department> get(){
		return departmentService.findAll();
	}
	
	@GetMapping("{id}")
	@ApiOperation("获取部门信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "部门ID", defaultValue = "2"),
	})
	public Department get(@PathVariable("id") int id) {
		return departmentService.getDepartment(id);
	}
	
	@PostMapping
	@ApiOperation("新增部门")
	public Department insert(@RequestBody Department department) {
		return departmentService.insertDepartment(department);
	}
	
	@PutMapping
	@ApiOperation("更新部门信息")
	public int update(@RequestBody Department department) {
		return departmentService.updateDepartment(department);
	}
	
	@DeleteMapping("{id}")
	@ApiOperation("删除部门接口")
	@ApiImplicitParam(name = "id", value = "部门ID", defaultValue = "2")
	public int delete(@PathVariable("id") int id) {
		return departmentService.deleteDepartment(id);
	}
}
