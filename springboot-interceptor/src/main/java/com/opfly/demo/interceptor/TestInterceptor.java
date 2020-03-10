package com.opfly.demo.interceptor;

import java.util.Base64;
import java.util.Base64.Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class TestInterceptor implements HandlerInterceptor {
	private String decryptBASE64(String data) {
		try {
			Decoder decoder = Base64.getDecoder();
	        byte[] buffer = decoder.decode(data);
	        return new String(buffer); 
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("执行前");
		try {
			String auth = request.getHeader("Authorization");
			String basic = auth.substring(0, 5).toLowerCase();
			if (basic.compareTo("basic") == 0) {
				String basicToken = auth.substring(6, auth.length());
				String decodedAuth = decryptBASE64(basicToken);
				String[] nameAndPwd = decodedAuth.split(":");
				String userName = nameAndPwd[0];
				String password = nameAndPwd[1];
				request.setAttribute("userName", userName);
				return true;
			}
		}catch (Exception e) {
		}
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		System.out.println("执行业务逻辑后");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		System.out.println("执行完成");
	}
}
