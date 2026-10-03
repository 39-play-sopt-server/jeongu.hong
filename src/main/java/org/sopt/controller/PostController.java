package org.sopt.controller;

import org.sopt.model.Post;
import org.sopt.model.PostRepository;
import org.sopt.view.PostView;

import java.util.ArrayList;
import java.util.List;

public class PostController {
    private static final int INVALID_INDEX = -1;

    private final PostRepository postRepository;
    private final PostView postView;

    public PostController(PostRepository postRepository, PostView postView) {
        this.postRepository = postRepository;
        this.postView = postView;
    }

    public void run() {
        while (true) {
            int command = postView.readCommand();

            switch (command) {
                case 1:
                    createPost();
                    break;

                case 2:
                    showPosts();
                    break;

                case 3:
                    showPost();
                    break;

                case 4:
                    updatePost();
                    break;

                case 5:
                    deletePost();
                    break;

                case 6:
                    postView.printMessage("프로그램을 종료합니다.");
                    return;

                default:
                    postView.printMessage("잘못된 입력입니다.");
            }
        }
    }

    private void createPost() {
        String title = postView.readTitle();
        String content = postView.readContent();

        postRepository.save(new Post(title, content));

        postView.printMessage("게시글이 작성되었습니다.");
    }

    private void showPosts() {
        List<String> titles = new ArrayList<>();

        for (Post post : postRepository.findAll()) {
            titles.add(post.getTitle());
        }

        postView.printPostList(titles);
    }

    private void showPost() {
        int index = readExistingIndex("조회할");

        if (index == INVALID_INDEX) {
            return;
        }

        Post post = postRepository.findByIndex(index);

        postView.printPost(post.getTitle(), post.getContent());
    }

    private void updatePost() {
        int index = readExistingIndex("수정할");

        if (index == INVALID_INDEX) {
            return;
        }

        Post post = postRepository.findByIndex(index);

        String newTitle = postView.readNewTitle();
        String newContent = postView.readNewContent();

        post.updateTitle(newTitle);
        post.updateContent(newContent);

        postView.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost() {
        int index = readExistingIndex("삭제할");

        if (index == INVALID_INDEX) {
            return;
        }

        postRepository.deleteByIndex(index);

        postView.printMessage("게시글이 삭제되었습니다.");
    }

    private int readExistingIndex(String action) {
        if (postRepository.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return INVALID_INDEX;
        }

        int index = postView.readPostIndex(action);

        if (!postRepository.exists(index)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return INVALID_INDEX;
        }

        return index;
    }
}
