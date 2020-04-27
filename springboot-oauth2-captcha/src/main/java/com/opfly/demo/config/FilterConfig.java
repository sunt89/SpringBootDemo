package com.opfly.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.opfly.demo.filter.CaptchaFilter;

@Configuration
public class FilterConfig {
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FilterRegistrationBean  captchaFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(captchaFilter());
		List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/oauth/token");
	    registrationBean.setUrlPatterns(urlPatterns);
	    return registrationBean;
    }
	
	@Bean
	public CaptchaFilter captchaFilter() {
		return new CaptchaFilter();
	}
}
