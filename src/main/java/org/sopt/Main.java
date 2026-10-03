package org.sopt;

import org.sopt.config.PostConfig;
import org.sopt.controller.PostController;
import org.sopt.model.PostRepository;
import org.sopt.view.PostView;

public class Main {

    public static void main(String[] args) {
        PostRepository postRepository = PostConfig.getRepository();
        PostView postView = new PostView();
        PostController controller = new PostController(postRepository, postView);
        controller.run();
    }
}
