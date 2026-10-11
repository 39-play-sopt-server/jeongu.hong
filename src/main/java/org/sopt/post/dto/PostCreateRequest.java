package org.sopt.post.dto;

import org.sopt.post.entity.Category;

public record PostCreateRequest(String title, String content, Category category) {
}
