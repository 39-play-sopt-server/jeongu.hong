package org.sopt.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUpdateRequest(
        @NotNull @NotBlank String title,
        @NotNull @NotBlank String content
) {
}
