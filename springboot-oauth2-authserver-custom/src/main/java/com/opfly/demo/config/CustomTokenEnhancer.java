package com.opfly.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.opfly.demo.pojo.User;

public class CustomTokenEnhancer implements TokenEnhancer{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		if (authentication.isClientOnly()) {
			return accessToken;
		}
		Map<String, Object> additionalInfo = new HashMap<>();
		User user = (User) authentication.getPrincipal();
        additionalInfo.put("email", user.getEmail());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
	}

}
