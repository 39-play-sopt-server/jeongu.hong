package org.sopt.global.security;

public interface TokenIssuer {

	String issueAccessToken(Long memberId);

	String issueRefreshToken(Long memberId);
}
