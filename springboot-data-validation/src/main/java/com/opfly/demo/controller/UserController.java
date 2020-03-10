package com.opfly.demo.controller;

import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opfly.demo.pojo.User;
import com.opfly.demo.result.ResultMsg;
import com.opfly.demo.result.ResultStatusCode;

@RestController
@RequestMapping("api/user")
public class UserController {
	@PostMapping
	public ResultMsg post(@Validated @RequestBody User user) {
		user.setId(UUID.randomUUID().toString());
		return ResultMsg.createResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(), user);
	}
}
