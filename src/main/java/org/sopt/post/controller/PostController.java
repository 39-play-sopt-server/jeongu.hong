package org.sopt.post.controller;

import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.service.PostService;
import org.sopt.post.view.PostView;

import java.util.List;

public class PostController {

    private final PostService postService;
    private final PostView postView;

    public PostController(PostService postService, PostView postView) {
        this.postService = postService;
        this.postView = postView;
    }

    public ApiResponse<Void> createPost() {
        PostCreateRequest request = new PostCreateRequest(
                postView.readTitle(),
                postView.readContent(),
                postView.readCategoryNumber(postService.getCategoryNames()));

        postService.createPost(request);
        return ApiResponse.success(SuccessCode.CREATED);
    }

    public ApiResponse<List<PostResponse>> getPosts() {
        return ApiResponse.success(SuccessCode.OK, postService.getPosts());
    }

    public ApiResponse<PostResponse> getPost() {
        long id = postView.readPostId("조회할");

        return ApiResponse.success(SuccessCode.OK, postService.getPost(id));
    }

    public ApiResponse<Void> updatePost() {
        long id = postView.readPostId("수정할");
        PostUpdateRequest request = new PostUpdateRequest(postView.readNewTitle(), postView.readNewContent());

        postService.updatePost(id, request);
        return ApiResponse.success(SuccessCode.UPDATED);
    }

    public ApiResponse<Void> deletePost() {
        long id = postView.readPostId("삭제할");

        postService.deletePost(id);
        return ApiResponse.success(SuccessCode.DELETED);
    }
}
