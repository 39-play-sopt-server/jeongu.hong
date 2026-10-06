package org.sopt.config;

import org.sopt.post.repository.HashMapPostRepository;
import org.sopt.post.repository.PostRepository;

public class PostConfig {

    private static final PostRepository postRepository = new HashMapPostRepository();

    public static PostRepository getRepository() {
        return postRepository;
    }
}
