package com.opfly.demo.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.opfly.demo.result.ArgumentInvalidResult;
import com.opfly.demo.result.ResultMsg;
import com.opfly.demo.result.ResultStatusCode;

@ControllerAdvice
//如果返回的为json数据或其它对象，添加该注解
@ResponseBody
public class GlobalExceptionHandler {
	//添加全局异常处理流程，根据需要设置需要处理的异常，本文以MethodArgumentNotValidException为例
	@ExceptionHandler(value=MethodArgumentNotValidException.class)
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public Object MethodArgumentNotValidHandler(HttpServletRequest request,
			MethodArgumentNotValidException exception) throws Exception
	{
		//按需重新封装需要返回的错误信息
		List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
		//解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
			invalidArgument.setDefaultMessage(error.getDefaultMessage());
			invalidArgument.setField(error.getField());
			invalidArgument.setRejectedValue(error.getRejectedValue());
			invalidArguments.add(invalidArgument);
		}
		
		return ResultMsg.createResultMsg(ResultStatusCode.PARAMETER_ERR.getErrcode(), ResultStatusCode.PARAMETER_ERR.getErrmsg(), invalidArguments);
	}
}
