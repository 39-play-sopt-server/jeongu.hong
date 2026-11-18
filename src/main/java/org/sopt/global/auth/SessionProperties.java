package org.sopt.global.auth;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

// 로그인 세션 정책 (토큰 구현 방식과 무관)
@ConfigurationProperties(prefix = "auth.session")
public record SessionProperties(
	Duration maxLifetime   // Rotation과 관계없이 로그인 후 세션이 유지되는 최대 기간
) {
}
