package com.opfly.demo.config;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.opfly.demo.service.CustomUserDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private static final String DEMO_RESOURCE_ID = "demo";
	
	@Autowired
    AuthenticationManager authenticationManager;
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	//@Autowired
	//UserDetailsService userDetailsService;
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
    	/*String finalSecret = passwordEncoder.encode("123456");
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
		        .accessTokenValiditySeconds(60 * 60 * 8);*/
    }
	
	/*//使用Reference Token,将token保存在内存中
	public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }*/
	
	//使用Json Web Token
	@Bean
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
    	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    	List<TokenEnhancer> tokenEnhancerList = Arrays.asList(new CustomTokenEnhancer(), accessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancerList);
        
        endpoints
	        .tokenStore(tokenStore())
	        .tokenEnhancer(tokenEnhancerChain)
	        .accessTokenConverter(accessTokenConverter())
	        .authenticationManager(authenticationManager)
	        //使用refresh_token获取token的时候需要添加UserDitailsService
            .userDetailsService(customUserDetailsService);
    }
	
	/*//Token保存在Redis中
	@Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }*/
	
	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    	oauthServer
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("permitAll()")
        .allowFormAuthenticationForClients();
    }
}
