package org.sopt;

import org.sopt.config.PostConfig;
import org.sopt.global.error.BusinessException;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.controller.PostController;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.view.PostView;

public class Main {

    private static final PostController controller = PostConfig.getController();
    private static final PostView postView = PostConfig.getView();

    public static void main(String[] args) {
        while (true) {
            try {
                int command = postView.readCommand();

                switch (command) {
                    case 1 -> createPost();
                    case 2 -> getPosts();
                    case 3 -> getPost();
                    case 4 -> updatePost();
                    case 5 -> deletePost();
                    case 6 -> {
                        postView.printMessage("프로그램을 종료합니다.");
                        return;
                    }
                    default -> postView.printMessage("잘못된 입력입니다.");
                }
            } catch (NumberFormatException e) {
                postView.printMessage("숫자로 입력해주세요.");
            } catch (BusinessException e) {
                postView.printResult(ApiResponse.fail(e.getErrorCode()));
            }
        }
    }

    private static void createPost() {
        String title = postView.readTitle();
        String content = postView.readContent();
        int categoryNumber = postView.readCategoryNumber(controller.getCategoryNames());
        PostCreateRequest request = new PostCreateRequest(title, content, categoryNumber);

        postView.printResult(controller.createPost(request));
    }

    private static void getPosts() {
        postView.printPostList(controller.getPosts());
    }

    private static void getPost() {
        long id = postView.readPostId("조회할");

        postView.printPost(controller.getPost(id));
    }

    private static void updatePost() {
        long id = postView.readPostId("수정할");
        String title = postView.readNewTitle();
        String content = postView.readNewContent();
        PostUpdateRequest request = new PostUpdateRequest(title, content);

        postView.printResult(controller.updatePost(id, request));
    }

    private static void deletePost() {
        long id = postView.readPostId("삭제할");

        postView.printResult(controller.deletePost(id));
    }
}
