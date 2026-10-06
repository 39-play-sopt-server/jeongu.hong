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

    public List<String> getCategoryNames() {
        return postService.getCategoryNames();
    }

    public List<PostResponse> getPosts() {
        return postService.getPosts();
    }

    public PostResponse getPost(long id) {
        return postService.getPost(id);
    }

    public void updatePost(long id, PostUpdateRequest request) {
        postService.updatePost(id, request);
    }

    public void deletePost(long id) {
        postService.deletePost(id);
    }
}
