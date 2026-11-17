package org.sopt.global.jwt;

import java.time.Duration;
import java.util.Date;

import javax.crypto.SecretKey;

import org.sopt.global.code.AuthErrorCode;
import org.sopt.global.error.BusinessException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final SecretKey secretKey;
	private final Duration accessTokenExpiration;

	public JwtProvider(JwtProperties jwtProperties) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret()));
		this.accessTokenExpiration = jwtProperties.accessTokenExpiration();
	}

	public String createAccessToken(Long memberId) {
		Date now = new Date();

		return Jwts.builder()
			.subject(String.valueOf(memberId))   // 누구의 토큰인지
			.issuedAt(now)                       // 발급 시각
			.expiration(new Date(now.getTime() + accessTokenExpiration.toMillis())) // 만료 시각
			.signWith(secretKey)                 // 서명
			.compact();
	}

	public Long getMemberId(String token) {
		return Long.valueOf(parseClaims(token).getSubject());
	}

	private Claims parseClaims(String token) {
		try {
			return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)   // 서명, 만료 검증
				.getPayload();
		} catch (ExpiredJwtException e) {
			throw new BusinessException(AuthErrorCode.EXPIRED_TOKEN);
		} catch (JwtException | IllegalArgumentException e) {
			throw new BusinessException(AuthErrorCode.INVALID_TOKEN);
		}
	}
}
