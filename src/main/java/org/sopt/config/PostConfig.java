package org.sopt.config;

import org.sopt.post.repository.HashMapRepository;
import org.sopt.post.repository.PostRepository;

public class PostConfig {

    private static final PostRepository postRepository = new HashMapRepository();

    public static PostRepository getRepository() {
        return postRepository;
    }
}
