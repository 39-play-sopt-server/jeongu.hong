package org.sopt.post.controller;

import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
           @RequestBody PostCreateRequest request
    ) {
        postService.createPost(request);
        return ApiResponse.success(SuccessCode.POST_CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts(
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        return ApiResponse.success(SuccessCode.POST_FETCHED, postService.getPosts());
    }

    @GetMapping(path = "/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable Long postId
    ) {
        return ApiResponse.success(SuccessCode.POST_FETCHED, postService.getPost(postId));
    }

    @PutMapping(path = "/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request) {
        postService.updatePost(postId, request);
        return ApiResponse.success(SuccessCode.POST_UPDATED);
    }

    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return ApiResponse.success(SuccessCode.POST_DELETED);
    }
}
