package org.sopt.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.post.domain.Category;

public record PostCreateRequest(
        @NotBlank(message = "제목은 비어있을 수 없습니다.") String title,
        @NotBlank(message = "본문은 비어있을 수 없습니다.") String content,
        @NotNull(message = "카테고리는 필수입니다.") Category category
) {
}
