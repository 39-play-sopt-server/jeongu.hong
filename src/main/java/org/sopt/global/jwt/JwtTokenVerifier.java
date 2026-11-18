package org.sopt.global.jwt;

import org.sopt.global.code.AuthErrorCode;
import org.sopt.global.error.BusinessException;
import org.sopt.global.security.TokenClaims;
import org.sopt.global.security.TokenVerifier;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;

@Component
public class JwtTokenVerifier implements TokenVerifier {

	private final JwtParser jwtParser;

	public JwtTokenVerifier(JwtParser jwtParser) {
		this.jwtParser = jwtParser;
	}

	@Override
	public TokenClaims verifyAccessToken(String token) {
		return verify(token, TokenType.ACCESS);
	}

	@Override
	public TokenClaims verifyRefreshToken(String token) {
		return verify(token, TokenType.REFRESH);
	}

	private TokenClaims verify(String token, TokenType expectedType) {
		Claims claims = parseClaims(token);
		if (!expectedType.matches(claims.get(TokenType.CLAIM_NAME, String.class))) {
			throw new BusinessException(AuthErrorCode.INVALID_TOKEN);
		}
		return toTokenClaims(claims);
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

	private TokenClaims toTokenClaims(Claims claims) {
		return new TokenClaims(
			Long.valueOf(claims.getSubject()),
			claims.getId(),
			claims.getExpiration().toInstant()
		);
	}
}
