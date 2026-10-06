package org.sopt.config;

import org.sopt.post.repository.PostRepository;
import org.sopt.post.repository.InMemoryPostRepository;

public class PostConfig {

    private static final PostRepository postRepository = new InMemoryPostRepository();

    public static PostRepository getRepository() {
        return postRepository;
    }
}
