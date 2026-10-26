package org.sopt.post.controller;

import org.sopt.global.response.BaseResponse;
import org.sopt.post.api.PostApi;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.domain.Category;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController implements PostApi {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> createPost(Long memberId, PostCreateRequest request) {
        postService.createPost(memberId, request);
        return BaseResponse.success(PostSuccessCode.POST_CREATED);
    }

    @Override
    public ResponseEntity<BaseResponse<Page<PostResponse>>> getPosts(Category category, Pageable pageable) {
        return BaseResponse.success(PostSuccessCode.POST_FETCHED, postService.getPosts(category, pageable));
    }

    @Override
    public ResponseEntity<BaseResponse<PostResponse>> getPost(Long postId) {
        return BaseResponse.success(PostSuccessCode.POST_FETCHED, postService.getPost(postId));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> updatePost(Long postId, PostUpdateRequest request) {
        postService.updatePost(postId, request);
        return BaseResponse.success(PostSuccessCode.POST_UPDATED);
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> deletePost(Long postId) {
        postService.deletePost(postId);
        return BaseResponse.success(PostSuccessCode.POST_DELETED);
    }
}
