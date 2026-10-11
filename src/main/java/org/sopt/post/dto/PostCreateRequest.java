package org.sopt.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.post.entity.Category;

public record PostCreateRequest(
        @NotNull @NotBlank String title,
        @NotNull @NotBlank String content,
        @NotNull Category category
) {
}
