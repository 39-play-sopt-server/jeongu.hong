package org.sopt.global.oauth;

import java.util.Arrays;

import org.sopt.global.code.AuthErrorCode;
import org.sopt.global.error.BusinessException;

public enum SocialProvider {
	KAKAO;

	public static SocialProvider from(String value) {
		return Arrays.stream(values())
			.filter(provider -> provider.name().equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new BusinessException(AuthErrorCode.UNSUPPORTED_SOCIAL_PROVIDER));
	}
}
