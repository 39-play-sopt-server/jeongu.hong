package org.sopt.post.handler;

import org.sopt.post.controller.PostController;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.view.InputView;
import org.sopt.post.view.OutputView;

public class UpdatePostHandler implements CommandHandler {

    private final InputView inputView;
    private final OutputView outputView;
    private final PostController controller;

    public UpdatePostHandler(InputView inputView, OutputView outputView, PostController controller) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.controller = controller;
    }

    @Override
    public void handle() {
        long id = inputView.readPostId("수정할");
        String title = inputView.readNewTitle();
        String content = inputView.readNewContent();
        PostUpdateRequest request = new PostUpdateRequest(title, content);

        outputView.printResult(controller.updatePost(id, request));
    }
}
