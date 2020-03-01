package com.opfly.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	/*@Autowired
	private DataSource dataSource;
	
	@Bean
    @Override
    protected UserDetailsService userDetailsService(){
        //内存用户
        //String finalPassword = passwordEncoder().encode("123456");
        //InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        //manager.createUser(User.withUsername("user_1").password(finalPassword).authorities("USER").build());
        //manager.createUser(User.withUsername("user_2").password(finalPassword).authorities("USER").build());
        //return manager;
		
		//默认Schema用户
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
		return manager;
    }*/
	
	/*
	 * 进行密码加密
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/*
	 * 重写authenticationManagerBean，并注册到容器中
	 */
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
	
	/*
	 * 重写configure，配置访问策略
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().anyRequest()
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/*").permitAll();
    }
}
