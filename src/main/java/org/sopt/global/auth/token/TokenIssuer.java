package org.sopt.global.auth.token;

public interface TokenIssuer {

	String issueAccessToken(Long memberId);

	String issueRefreshToken(Long memberId);
}
