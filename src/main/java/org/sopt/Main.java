package org.sopt;

import org.sopt.config.PostConfig;
import org.sopt.global.error.BusinessException;
import org.sopt.global.error.ErrorCode;
import org.sopt.post.controller.PostController;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.view.PostView;

public class Main {

    public static void main(String[] args) {
        PostController controller = PostConfig.getController();
        PostView postView = new PostView();

        while (true) {
            try {
                int command = postView.readCommand();

                switch (command) {
                    case 1:
                        controller.createPost(new PostCreateRequest(
                                postView.readTitle(),
                                postView.readContent(),
                                postView.readCategoryNumber(controller.getCategoryNames())));
                        postView.printMessage("게시글이 작성되었습니다.");
                        break;

                    case 2:
                        postView.printPostList(controller.getPosts());
                        break;

                    case 3:
                        postView.printPost(controller.getPost(postView.readPostId("조회할")));
                        break;

                    case 4:
                        controller.updatePost(
                                postView.readPostId("수정할"),
                                new PostUpdateRequest(postView.readNewTitle(), postView.readNewContent()));
                        postView.printMessage("게시글이 수정되었습니다.");
                        break;

                    case 5:
                        controller.deletePost(postView.readPostId("삭제할"));
                        postView.printMessage("게시글이 삭제되었습니다.");
                        break;

                    case 6:
                        postView.printMessage("프로그램을 종료합니다.");
                        return;

                    default:
                        postView.printMessage("잘못된 입력입니다.");
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
