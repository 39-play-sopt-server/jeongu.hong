package org.sopt;

import org.sopt.controller.PostController;
import org.sopt.model.PostRepository;
import org.sopt.view.PostView;

public class Main {

    public static void main(String[] args) {
        PostController controller = new PostController(new PostRepository(), new PostView());
        controller.run();
    }
}
