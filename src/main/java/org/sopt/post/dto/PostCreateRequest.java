package org.sopt.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.post.domain.Category;

public record PostCreateRequest(
        @NotBlank String title,
        @NotBlank String content,
        @NotNull Category category
) {
}
