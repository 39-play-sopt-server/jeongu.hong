package org.sopt.post.dto;

import org.sopt.post.entity.Post;

public record PostResponse(String title, String content, String category) {

    public static PostResponse from(Post post) {
        return new PostResponse(post.getTitle(), post.getContent(), post.getCategory().name());
    }
}
