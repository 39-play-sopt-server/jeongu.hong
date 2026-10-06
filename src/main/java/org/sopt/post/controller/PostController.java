package org.sopt.post.controller;

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

    public void createPost(PostCreateRequest request) {
        postService.createPost(request);
    }

    public List<PostResponse> getPosts() {
        return postService.getPosts();
    }

    public PostResponse getPost(int index) {
        return postService.getPost(index);
    }

    public void updatePost(int index, PostUpdateRequest request) {
        postService.updatePost(index, request);
    }

    public void deletePost(int index) {
        postService.deletePost(index);
    }
}
