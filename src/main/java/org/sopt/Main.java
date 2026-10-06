package org.sopt;

import org.sopt.config.PostConfig;
import org.sopt.post.controller.PostController;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.PostService;
import org.sopt.post.view.PostView;

public class Main {

    public static void main(String[] args) {
        PostRepository postRepository = PostConfig.getRepository();
        PostView postView = new PostView();
        PostService postService = new PostService(postRepository);
        PostController controller = new PostController(postService);

        while (true) {
            try {
                int command = postView.readCommand();

                switch (command) {
                    case 1:
                        controller.createPost(new PostCreateRequest(
                                postView.readTitle(),
                                postView.readContent(),
                                postView.readCategoryNumber()));
                        postView.printMessage("게시글이 작성되었습니다.");
                        break;

                    case 2:
                        postView.printPostList(controller.getPosts());
                        break;

                    case 3:
                        postView.printPost(controller.getPost(postView.readPostIndex("조회할")));
                        break;

                    case 4:
                        controller.updatePost(
                                postView.readPostIndex("수정할"),
                                new PostUpdateRequest(postView.readNewTitle(), postView.readNewContent()));
                        postView.printMessage("게시글이 수정되었습니다.");
                        break;

                    case 5:
                        controller.deletePost(postView.readPostIndex("삭제할"));
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
            } catch (IllegalArgumentException e) {
                postView.printMessage(e.getMessage());
            }
        }
    }
}
