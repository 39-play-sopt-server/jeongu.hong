package org.sopt.config;

import org.sopt.post.controller.PostController;
import org.sopt.post.repository.HashMapPostRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.PostService;
import org.sopt.post.view.PostView;

public class PostConfig {

    private static final PostRepository postRepository = new HashMapPostRepository();
    private static final PostService postService = new PostService(postRepository);
    private static final PostView postView = new PostView();
    private static final PostController postController = new PostController(postService);

    public static PostController getController() {
        return postController;
    }

    public static PostView getView() {
        return postView;
    }
}
