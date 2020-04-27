package com.opfly.demo.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.opfly.demo.service.CustomUserDetailsService;

public class SmsTokenGranter extends AbstractTokenGranter {
	@Autowired
    private CustomUserDetailsService userDetailsService;
	
	private static final String GRANT_TYPE = "sms";

	public SmsTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
			OAuth2RequestFactory requestFactory, CustomUserDetailsService userDetailsService) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userDetailsService = userDetailsService;
	}
	
	@Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> params = tokenRequest.getRequestParameters();
        String phoneNum = params.getOrDefault("phone_num", "");
        UserDetails userDetails = userDetailsService.loadUserByPhoneNum(phoneNum);
        Authentication user = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.getAuthorities());
        return new OAuth2Authentication(tokenRequest.createOAuth2Request(client), user);
    }
}
