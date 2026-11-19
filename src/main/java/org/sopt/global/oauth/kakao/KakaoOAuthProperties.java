package org.sopt.global.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOAuthProperties(
	@NotBlank String clientId,
	@NotBlank String clientSecret,
	@NotBlank String redirectUri,
	@NotBlank String tokenUri,
	@NotBlank String userInfoUri
) {
}