package org.sopt.post.handler;

import org.sopt.post.controller.PostController;
import org.sopt.post.view.InputView;
import org.sopt.post.view.OutputView;

public class DeletePostHandler implements CommandHandler {

    private final InputView inputView;
    private final OutputView outputView;
    private final PostController controller;

    public DeletePostHandler(InputView inputView, OutputView outputView, PostController controller) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.controller = controller;
    }

    @Override
    public void handle() {
        long id = inputView.readPostId("삭제할");

        outputView.printResult(controller.deletePost(id));
    }
}
