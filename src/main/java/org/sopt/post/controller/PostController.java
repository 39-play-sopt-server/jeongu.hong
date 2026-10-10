package org.sopt.post.controller;

import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.service.PostService;
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
    public ApiResponse<Void> createPost(
           @RequestBody PostCreateRequest request
    ) {
        postService.createPost(request);
        return ApiResponse.success(SuccessCode.CREATED);
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts(
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        return ApiResponse.success(SuccessCode.OK, postService.getPosts());
    }

    @GetMapping(path = "/{postId}")
    public ApiResponse<PostResponse> getPost(
            @PathVariable Long postId
    ) {
        return ApiResponse.success(SuccessCode.OK, postService.getPost(postId));
    }

    @PutMapping(path = "/{postId}")
    public ApiResponse<Void> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request) {
        postService.updatePost(postId, request);
        return ApiResponse.success(SuccessCode.UPDATED);
    }

    @DeleteMapping(path = "/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return ApiResponse.success(SuccessCode.DELETED);
    }
}
