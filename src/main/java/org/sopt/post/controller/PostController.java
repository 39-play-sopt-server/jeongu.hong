package org.sopt.post.controller;

import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.service.PostService;

import java.util.List;

public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public ApiResponse<Void> createPost(PostCreateRequest request) {
        postService.createPost(request);
        return ApiResponse.success(SuccessCode.CREATED);
    }

    public List<String> getCategoryNames() {
        return postService.getCategoryNames();
    }

    public ApiResponse<List<PostResponse>> getPosts() {
        return ApiResponse.success(SuccessCode.OK, postService.getPosts());
    }

    public ApiResponse<PostResponse> getPost(long id) {
        return ApiResponse.success(SuccessCode.OK, postService.getPost(id));
    }

    public ApiResponse<Void> updatePost(long id, PostUpdateRequest request) {
        postService.updatePost(id, request);
        return ApiResponse.success(SuccessCode.UPDATED);
    }

    public ApiResponse<Void> deletePost(long id) {
        postService.deletePost(id);
        return ApiResponse.success(SuccessCode.DELETED);
    }
}
