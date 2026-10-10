package org.sopt.post.handler;

import org.sopt.post.controller.PostController;
import org.sopt.post.view.OutputView;

public class GetPostsHandler implements CommandHandler {

    private final OutputView outputView;
    private final PostController controller;

    public GetPostsHandler(OutputView outputView, PostController controller) {
        this.outputView = outputView;
        this.controller = controller;
    }

    @Override
    public void handle() {
        outputView.printPostList(controller.getPosts());
    }
}
