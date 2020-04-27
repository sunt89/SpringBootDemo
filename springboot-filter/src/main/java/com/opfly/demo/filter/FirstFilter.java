package com.opfly.demo.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

//@WebFilter(filterName="firstfilter", urlPatterns= {"/api/*"})
public class FirstFilter implements Filter {
	
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
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Start First Filter");
		try {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String auth = httpRequest.getHeader("Authorization");
			String basic = auth.substring(0, 5).toLowerCase();
			if (basic.compareTo("basic") == 0) {
				String basicToken = auth.substring(6, auth.length());
				String decodedAuth = decryptBASE64(basicToken);
				String[] nameAndPwd = decodedAuth.split(":");
				String userName = nameAndPwd[0];
				String password = nameAndPwd[1];
				request.setAttribute("userName", userName);
				chain.doFilter(request, response);
				return;
			}
		}catch (Exception e) {
		}
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");  
		httpResponse.setContentType("application/json; charset=utf-8"); 
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.getWriter().write("failed");
		System.out.println("End First Filter");
	}
	
	@Override
	public void destroy() {
		
	}
}
