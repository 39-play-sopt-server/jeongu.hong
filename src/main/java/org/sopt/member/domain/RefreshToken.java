package org.sopt.member.domain;

import java.time.LocalDateTime;

import org.sopt.global.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private Long memberId;

	@Column(nullable = false, length = 512)
	private String token;

	@Column(nullable = false)
	private LocalDateTime sessionExpiresAt;

	private RefreshToken(Long memberId, String token, LocalDateTime sessionExpiresAt) {
		this.memberId = memberId;
		this.token = token;
		this.sessionExpiresAt = sessionExpiresAt;
	}

	public static RefreshToken create(Long memberId, String token, LocalDateTime sessionExpiresAt) {
		return new RefreshToken(memberId, token, sessionExpiresAt);
	}

	public void renew(String token, LocalDateTime sessionExpiresAt) {
		this.token = token;
		this.sessionExpiresAt = sessionExpiresAt;
	}

	public void rotate(String newToken) {
		this.token = newToken;
	}

	public boolean matches(String token) {
		return this.token.equals(token);
	}

	public boolean isSessionExpired(LocalDateTime now) {
		return now.isAfter(sessionExpiresAt);
	}
}
