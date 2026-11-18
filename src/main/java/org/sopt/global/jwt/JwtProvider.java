package org.sopt.global.jwt;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.sopt.global.code.AuthErrorCode;
import org.sopt.global.error.BusinessException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Component
public class JwtProvider {

	private static final String TOKEN_TYPE_CLAIM = "type";

	private final SecretKey secretKey;
	private final JwtParser jwtParser;
	private final Duration accessTokenExpiration;
	private final Duration refreshTokenExpiration;

	public JwtProvider(SecretKey secretKey, JwtParser jwtParser, JwtProperties jwtProperties) {
		this.secretKey = secretKey;
		this.jwtParser = jwtParser;
		this.accessTokenExpiration = jwtProperties.accessTokenExpiration();
		this.refreshTokenExpiration = jwtProperties.refreshTokenExpiration();
	}

	public String createAccessToken(Long memberId) {
		return createToken(memberId, TokenType.ACCESS, accessTokenExpiration);
	}

	public String createRefreshToken(Long memberId) {
		return createToken(memberId, TokenType.REFRESH, refreshTokenExpiration);
	}

	private String createToken(Long memberId, TokenType tokenType, Duration expiration) {
		Date now = new Date();

		return Jwts.builder()
			.id(UUID.randomUUID().toString())
			.subject(String.valueOf(memberId))
			.claim(TOKEN_TYPE_CLAIM, tokenType.getValue())
			.issuedAt(now)
			.expiration(new Date(now.getTime() + expiration.toMillis()))
			.signWith(secretKey)
			.compact();
	}

	public Long getMemberIdFromAccessToken(String token) {
		return getMemberId(token, TokenType.ACCESS);
	}

	public Long getMemberIdFromRefreshToken(String token) {
		return getMemberId(token, TokenType.REFRESH);
	}

	private Long getMemberId(String token, TokenType expectedType) {
		Claims claims = parseClaims(token);
		if (!expectedType.matches(claims.get(TOKEN_TYPE_CLAIM, String.class))) {
			throw new BusinessException(AuthErrorCode.INVALID_TOKEN);
		}
		return Long.valueOf(claims.getSubject());
	}

	private Claims parseClaims(String token) {
		try {
			return jwtParser.parseSignedClaims(token)
				.getPayload();
		} catch (ExpiredJwtException e) {
			throw new BusinessException(AuthErrorCode.EXPIRED_TOKEN);
		} catch (JwtException | IllegalArgumentException e) {
			throw new BusinessException(AuthErrorCode.INVALID_TOKEN);
		}
	}
}
