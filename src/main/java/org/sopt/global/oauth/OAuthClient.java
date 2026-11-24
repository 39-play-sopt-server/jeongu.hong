package org.sopt.global.oauth;

public interface OAuthClient {

	SocialProvider provider();

	OAuthUserInfo fetchUserInfo(String authorizationCode);
}
