package org.sopt.post.dto;

import org.sopt.post.domain.Post;

public record PostResponse(Long id, String title, String content, String category, Long viewCount) {

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCategory().name(), post.getViewCount());
    }
}
