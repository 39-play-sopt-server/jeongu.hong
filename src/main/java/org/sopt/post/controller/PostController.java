package org.sopt.post.controller;

import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.BaseResponse;
import org.sopt.post.api.PostApi;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController implements PostApi {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> createPost(PostCreateRequest request) {
        postService.createPost(request);
        return BaseResponse.success(SuccessCode.POST_CREATED);
    }

    @Override
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPosts(int page, int size) {
        return BaseResponse.success(SuccessCode.POST_FETCHED, postService.getPosts(page, size));
    }

    @Override
    public ResponseEntity<BaseResponse<PostResponse>> getPost(Long postId) {
        return BaseResponse.success(SuccessCode.POST_FETCHED, postService.getPost(postId));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> updatePost(Long postId, PostUpdateRequest request) {
        postService.updatePost(postId, request);
        return BaseResponse.success(SuccessCode.POST_UPDATED);
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> deletePost(Long postId) {
        postService.deletePost(postId);
        return BaseResponse.success(SuccessCode.POST_DELETED);
    }
}
