package org.sopt.global.auth.token;

import java.time.Instant;

import org.sopt.global.auth.Role;

public record TokenClaims(
	Long memberId,
	String jti,
	Instant expiresAt,
	Role role
) {
}