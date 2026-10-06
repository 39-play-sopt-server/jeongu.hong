package org.sopt;

import org.sopt.config.PostConfig;
import org.sopt.global.error.BusinessException;
import org.sopt.global.error.ErrorCode;
import org.sopt.post.controller.PostController;
import org.sopt.post.view.PostView;

public class Main {

    public static void main(String[] args) {
        PostController controller = PostConfig.getController();
        PostView postView = PostConfig.getView();

        while (true) {
            try {
                int command = postView.readCommand();

                switch (command) {
                    case 1 -> controller.createPost();
                    case 2 -> controller.getPosts();
                    case 3 -> controller.getPost();
                    case 4 -> controller.updatePost();
                    case 5 -> controller.deletePost();
                    case 6 -> {
                        postView.printMessage("프로그램을 종료합니다.");
                        return;
                    }
                    default -> postView.printMessage("잘못된 입력입니다.");
                }
            } catch (NumberFormatException e) {
                postView.printMessage("숫자로 입력해주세요.");
            } catch (BusinessException e) {
                ErrorCode errorCode = e.getErrorCode();
                postView.printError(errorCode.getCode(), errorCode.getMessage());
            }
        }
    }
}
