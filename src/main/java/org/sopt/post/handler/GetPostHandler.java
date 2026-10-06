package org.sopt.post.handler;

import org.sopt.post.controller.PostController;
import org.sopt.post.view.InputView;
import org.sopt.post.view.OutputView;

public class GetPostHandler implements CommandHandler {

    private final InputView inputView;
    private final OutputView outputView;
    private final PostController controller;

    public GetPostHandler(InputView inputView, OutputView outputView, PostController controller) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.controller = controller;
    }

    @Override
    public void handle() {
        long id = inputView.readPostId("조회할");

        outputView.printPost(controller.getPost(id));
    }
}
