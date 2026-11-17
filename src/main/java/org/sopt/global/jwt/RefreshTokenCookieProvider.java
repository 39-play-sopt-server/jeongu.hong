package org.sopt.global.jwt;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenCookieProvider {

	public static final String COOKIE_NAME = "refreshToken";
	private static final String COOKIE_PATH = "/api/v1/auth";

	private final JwtProperties jwtProperties;
	private final CookieProperties cookieProperties;

	public ResponseCookie create(String refreshToken) {
		return baseCookie(refreshToken)
			.maxAge(jwtProperties.refreshTokenExpiration())
			.build();
	}

	public ResponseCookie delete() {
		return baseCookie("")
			.maxAge(0)
			.build();
	}

	private ResponseCookie.ResponseCookieBuilder baseCookie(String value) {
		return ResponseCookie.from(COOKIE_NAME, value)
			.httpOnly(true)
			.secure(cookieProperties.secure())
			.sameSite(cookieProperties.sameSite())
			.path(COOKIE_PATH);
	}
}
