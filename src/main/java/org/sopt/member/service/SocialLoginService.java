package org.sopt.member.service;

import org.sopt.global.auth.token.TokenPair;
import org.sopt.global.oauth.OAuthClients;
import org.sopt.global.oauth.OAuthUserInfo;
import org.sopt.global.oauth.SocialProvider;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

	private final OAuthClients oAuthClients;
	private final AuthService authService;

	public TokenPair login(SocialProvider provider, String code) {
		OAuthUserInfo userInfo = oAuthClients.get(provider).fetchUserInfo(code);
		return authService.loginWithSocial(userInfo);
	}
}