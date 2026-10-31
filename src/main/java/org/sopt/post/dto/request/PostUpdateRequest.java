package org.sopt.post.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateRequest(
        @NotBlank(message = "제목은 비어있을 수 없습니다.") String title,
        @NotBlank(message = "본문은 비어있을 수 없습니다.") String content
) {
}
