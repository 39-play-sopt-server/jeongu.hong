package org.sopt.global.security;

import java.time.Instant;

public record TokenClaims(
	Long memberId,
	String jti,
	Instant expiresAt
) {
}