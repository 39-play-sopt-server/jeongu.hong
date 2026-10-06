package org.sopt.post.handler;

import org.sopt.post.controller.PostController;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.view.InputView;
import org.sopt.post.view.OutputView;

public class CreatePostHandler implements CommandHandler {

    private final InputView inputView;
    private final OutputView outputView;
    private final PostController controller;

    public CreatePostHandler(InputView inputView, OutputView outputView, PostController controller) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.controller = controller;
    }

    @Override
    public void handle() {
        String title = inputView.readTitle();
        String content = inputView.readContent();
        int categoryNumber = inputView.readCategoryNumber(controller.getCategoryNames());
        PostCreateRequest request = new PostCreateRequest(title, content, categoryNumber);

        outputView.printResult(controller.createPost(request));
    }
}
