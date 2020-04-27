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

public class CaptchaFilter extends OncePerRequestFilter{
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String grant_type = request.getParameter("grant_type");
		if(request.getMethod().equalsIgnoreCase("post")
				&& grant_type != null
				&& grant_type.equalsIgnoreCase("password")){
			String captchaId = request.getParameter("captcha_id");
			String captchaCode = request.getParameter("captcha_code");
			if (!StringUtils.isEmpty(captchaId)
					&& !StringUtils.isEmpty(captchaCode)) {
				String captcha = stringRedisTemplate.opsForValue().get(captchaId);
				if (!StringUtils.isEmpty(captcha)) {
					stringRedisTemplate.delete(captchaId);
				}
				
				if (captchaCode.equals(captcha)) {
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
        }else {
        	filterChain.doFilter(request, response);
        }
	}
}
