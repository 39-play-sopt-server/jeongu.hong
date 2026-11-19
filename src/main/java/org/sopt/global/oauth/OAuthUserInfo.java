package org.sopt.global.oauth;

public record OAuthUserInfo(
	SocialProvider provider,
	String providerId,
	String nickname
) {
}
