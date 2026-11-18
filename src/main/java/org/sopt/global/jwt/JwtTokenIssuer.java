package org.sopt.global.jwt;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.sopt.global.security.TokenIssuer;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenIssuer implements TokenIssuer {

	private final SecretKey secretKey;
	private final Duration accessTokenExpiration;
	private final Duration refreshTokenExpiration;

	public JwtTokenIssuer(SecretKey secretKey, JwtProperties jwtProperties) {
		this.secretKey = secretKey;
		this.accessTokenExpiration = jwtProperties.accessTokenExpiration();
		this.refreshTokenExpiration = jwtProperties.refreshTokenExpiration();
	}

	@Override
	public String issueAccessToken(Long memberId) {
		return issue(memberId, TokenType.ACCESS, accessTokenExpiration);
	}

	@Override
	public String issueRefreshToken(Long memberId) {
		return issue(memberId, TokenType.REFRESH, refreshTokenExpiration);
	}

	private String issue(Long memberId, TokenType tokenType, Duration expiration) {
		Date now = new Date();

		return Jwts.builder()
			.id(UUID.randomUUID().toString())
			.subject(String.valueOf(memberId))
			.claim(TokenType.CLAIM_NAME, tokenType.getValue())
			.issuedAt(now)
			.expiration(new Date(now.getTime() + expiration.toMillis()))
			.signWith(secretKey)
			.compact();
	}
}