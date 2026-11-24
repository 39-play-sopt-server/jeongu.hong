package org.sopt.global.auth;

import java.time.Instant;

import org.sopt.global.auth.token.TokenClaims;

public record AuthMember(
	Long memberId,
	String tokenId,
	Instant tokenExpiresAt
) {

	public static AuthMember from(TokenClaims claims) {
		return new AuthMember(claims.memberId(), claims.jti(), claims.expiresAt());
	}
}
