package com.opfly.demo.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SmsFilter extends OncePerRequestFilter {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (request.getMethod().equalsIgnoreCase("post")) {
			String grant_type = request.getParameter("grant_type");
			if("sms".equalsIgnoreCase(grant_type)){
				String phoneNum = request.getParameter("phone_num");
				String smsCode = request.getParameter("sms_code");
				if (!StringUtils.isEmpty(phoneNum)
						&& !StringUtils.isEmpty(smsCode)) {
					String captcha = stringRedisTemplate.opsForValue().get(phoneNum);
					if (!StringUtils.isEmpty(captcha)) {
						stringRedisTemplate.delete(phoneNum);
					}
					
					if (smsCode.equals(captcha)) {
						filterChain.doFilter(request, response);
						return;
					}
				}
	
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("application/json; charset=utf-8"); 
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				HashMap<String, String> result = new HashMap<>();
				result.put("error", "invalid_captcha");
				result.put("error_description", "验证码错误");
				ObjectMapper mapper = new ObjectMapper();
				response.getWriter().write(mapper.writeValueAsString(result));
				return;
	        }
		}
		filterChain.doFilter(request, response);
	}
}
