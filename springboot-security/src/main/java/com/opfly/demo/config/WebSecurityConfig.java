package com.opfly.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	/*
	 * 重写userDetailsService，在内存中添加测试用户
	 */
	@Bean
    @Override
    protected UserDetailsService userDetailsService(){
        String finalPassword = passwordEncoder().encode("123456");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(finalPassword).roles("USER", "ADMIN").build());
        manager.createUser(User.withUsername("user").password(finalPassword).roles("USER").build());
        return manager;
    }
	
	/*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("USER","ADMIN");
    }*/
	
	/*
	 * 进行密码加密
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/**
     *	 匹配 "/","/index" 路径，不需要权限即可访问
     *	 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 	匹配 "/admin" 及其以下所有路径，都需要 "ADMIN" 权限
     * 	登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 	退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		 http
         .authorizeRequests()
         .antMatchers("/", "/index", "/error").permitAll()
         .antMatchers("/user/**").hasRole("USER")
         .antMatchers("/admin/**").hasRole("ADMIN")
         .and()
         .formLogin().loginPage("/login").defaultSuccessUrl("/user")
         .and()
         .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }
}
