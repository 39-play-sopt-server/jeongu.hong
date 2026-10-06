package org.sopt;

import org.sopt.config.PostConfig;
import org.sopt.global.error.BusinessException;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.controller.PostController;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.view.InputView;
import org.sopt.post.view.OutputView;

public class Main {

    private static final PostController controller = PostConfig.getController();
    private static final InputView inputView = PostConfig.getInputView();
    private static final OutputView outputView = PostConfig.getOutputView();

    public static void main(String[] args) {
        while (true) {
            try {
                int command = inputView.readCommand();

                switch (command) {
                    case 1 -> createPost();
                    case 2 -> getPosts();
                    case 3 -> getPost();
                    case 4 -> updatePost();
                    case 5 -> deletePost();
                    case 6 -> {
                        outputView.printMessage("프로그램을 종료합니다.");
                        return;
                    }
                    default -> outputView.printMessage("잘못된 입력입니다.");
                }
            } catch (BusinessException e) {
                outputView.printResult(ApiResponse.fail(e.getErrorCode()));
            }
        }
    }

    private static void createPost() {
        String title = inputView.readTitle();
        String content = inputView.readContent();
        int categoryNumber = inputView.readCategoryNumber(controller.getCategoryNames());
        PostCreateRequest request = new PostCreateRequest(title, content, categoryNumber);

        outputView.printResult(controller.createPost(request));
    }

    private static void getPosts() {
        outputView.printPostList(controller.getPosts());
    }

    private static void getPost() {
        long id = inputView.readPostId("조회할");

        outputView.printPost(controller.getPost(id));
    }

    private static void updatePost() {
        long id = inputView.readPostId("수정할");
        String title = inputView.readNewTitle();
        String content = inputView.readNewContent();
        PostUpdateRequest request = new PostUpdateRequest(title, content);

        outputView.printResult(controller.updatePost(id, request));
    }

    private static void deletePost() {
        long id = inputView.readPostId("삭제할");

        outputView.printResult(controller.deletePost(id));
    }
}
