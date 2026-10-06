package org.sopt.post.controller;

import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.service.PostService;
import org.sopt.post.view.PostView;

public class PostController {

    private final PostService postService;
    private final PostView postView;

    public PostController(PostService postService, PostView postView) {
        this.postService = postService;
        this.postView = postView;
    }

    public void createPost() {
        PostCreateRequest request = new PostCreateRequest(
                postView.readTitle(),
                postView.readContent(),
                postView.readCategoryNumber(postService.getCategoryNames()));

        postService.createPost(request);
        postView.printMessage("게시글이 작성되었습니다.");
    }

    public void getPosts() {
        postView.printPostList(postService.getPosts());
    }

    public void getPost() {
        long id = postView.readPostId("조회할");

        postView.printPost(postService.getPost(id));
    }

    public void updatePost() {
        long id = postView.readPostId("수정할");
        PostUpdateRequest request = new PostUpdateRequest(postView.readNewTitle(), postView.readNewContent());

        postService.updatePost(id, request);
        postView.printMessage("게시글이 수정되었습니다.");
    }

    public void deletePost() {
        long id = postView.readPostId("삭제할");

        postService.deletePost(id);
        postView.printMessage("게시글이 삭제되었습니다.");
    }
}
