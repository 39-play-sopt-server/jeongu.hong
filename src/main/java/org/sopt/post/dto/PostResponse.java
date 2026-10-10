package org.sopt.post.dto;

import org.sopt.post.entity.Post;

public record PostResponse(long id, String title, String content, String category) {

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCategory().name());
    }
}
