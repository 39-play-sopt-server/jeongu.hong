package org.sopt.controller;

import org.sopt.model.Post;
import org.sopt.model.PostRepository;
import org.sopt.view.PostView;

import java.util.ArrayList;
import java.util.List;

public class PostController {
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
        if (postRepository.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        List<String> titles = new ArrayList<>();

        for (Post post : postRepository.findAll()) {
            titles.add(post.getTitle());
        }

        postView.printPostList(titles);
    }

    private void showPost() {
        if (postRepository.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int index = postView.readPostIndex("조회할");

        if (!postRepository.exists(index)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        Post post = postRepository.findByIndex(index);

        postView.printPost(post.getTitle(), post.getContent());
    }

    private void updatePost() {
        if (postRepository.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int index = postView.readPostIndex("수정할");

        if (!postRepository.exists(index)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        String newTitle = postView.readNewTitle();
        String newContent = postView.readNewContent();

        Post post = postRepository.findByIndex(index);

        post.updateTitle(newTitle);
        post.updateContent(newContent);

        postView.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost() {
        if (postRepository.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }

        int index = postView.readPostIndex("삭제할");

        if (!postRepository.exists(index)) {
            postView.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        postRepository.deleteByIndex(index);

        postView.printMessage("게시글이 삭제되었습니다.");
    }
}
