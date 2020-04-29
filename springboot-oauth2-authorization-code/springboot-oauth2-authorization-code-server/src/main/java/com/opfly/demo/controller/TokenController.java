package com.opfly.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
public class TokenController {
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	ConsumerTokenServices tokenServices;
	
	@DeleteMapping("revoke")
	public void revokeToken() {
		final OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder
	            .getContext().getAuthentication();
	    final String token = tokenStore.getAccessToken(auth).getValue();
	    System.out.println(token);
	    tokenServices.revokeToken(token);
	}
}
