package org.sopt.global.security;

public interface TokenVerifier {

	TokenClaims verifyAccessToken(String token);

	TokenClaims verifyRefreshToken(String token);
}
