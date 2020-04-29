package com.opfly.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private static final String DEMO_RESOURCE_ID = "demo";
	
	@Autowired
    AuthenticationManager authenticationManager;
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsService userDetailService;
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	String finalSecret = passwordEncoder.encode("123456");
        //配置两个客户端,一个用于password认证一个用于client认证
    	clients.inMemory()
				.withClient("client01")
		        .resourceIds(DEMO_RESOURCE_ID)
		        .authorizedGrantTypes("password", "refresh_token")
		        .scopes("api")
		        .secret(finalSecret)
		        .accessTokenValiditySeconds(60 * 60 * 8)
		        .refreshTokenValiditySeconds(60 * 60 * 24 * 7)
		        .and()
		        .withClient("client02")
		        .resourceIds(DEMO_RESOURCE_ID)
		        .authorizedGrantTypes("client_credentials")
		        .scopes("api")
		        .secret(finalSecret)
		        .accessTokenValiditySeconds(60 * 60 * 8);
    }
	
	/*
	 * 将token保存在内存中
	 */
	//使用Reference Token
	@Bean
	public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService); //使用refresh_token时需要添加
    }
	
	//使用Json Web Token
	/*@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("111111");
        return converter;
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService);
    }*/
	
	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    	oauthServer
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        .allowFormAuthenticationForClients();
    }
}
