package org.sopt.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OAuthLoginRequest(
	@NotBlank(message = "인가 코드는 필수입니다.") String code
) {
}
