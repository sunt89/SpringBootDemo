package com.opfly.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.opfly.demo.filter.FirstFilter;
import com.opfly.demo.filter.SecondFilter;

@Configuration
public class FilterConfig {
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FilterRegistrationBean  firstFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		FirstFilter firstFilter = new FirstFilter();
		registrationBean.setFilter(firstFilter);
		List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/api/*");
	    registrationBean.setUrlPatterns(urlPatterns);
	    registrationBean.setOrder(2); //order的数值越小，在所有的filter中优先级越高
	    return registrationBean;
    }
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FilterRegistrationBean  secondFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		SecondFilter secondFilter = new SecondFilter();
		registrationBean.setFilter(secondFilter);
		List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/api/*");
	    registrationBean.setUrlPatterns(urlPatterns);
	    registrationBean.setOrder(1); //order的数值越小，在所有的filter中优先级越高
	    return registrationBean;
    }
}
