package org.sopt.global.auth.token;

import java.time.Instant;

public record TokenClaims(
	Long memberId,
	String jti,
	Instant expiresAt
) {
}