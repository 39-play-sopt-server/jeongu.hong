package org.sopt.global.auth.token;

public interface TokenVerifier {

	TokenClaims verifyAccessToken(String token);

	TokenClaims verifyRefreshToken(String token);
}
