package org.sopt;

import org.sopt.controller.PostController;
import org.sopt.model.PostRepositoryImpl;
import org.sopt.view.PostView;

public class Main {

    public static void main(String[] args) {
        PostController controller = new PostController(new PostRepositoryImpl(), new PostView());
        controller.run();
    }
}
