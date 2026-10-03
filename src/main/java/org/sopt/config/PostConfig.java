package org.sopt.config;

import org.sopt.model.PostRepository;
import org.sopt.model.InMemoryPostRepository;

public class PostConfig {

    private static final PostRepository postRepository = new InMemoryPostRepository();

    public static PostRepository getRepository() {
        return postRepository;
    }
}
