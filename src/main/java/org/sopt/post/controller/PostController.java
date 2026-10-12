package org.sopt.post.controller;

import org.sopt.global.response.BaseResponse;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.api.PostApi;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.domain.Category;
import org.sopt.post.facade.PostLockFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController implements PostApi {

    private final PostLockFacade postLockFacade;

    public PostController(PostLockFacade postLockFacade) {
        this.postLockFacade = postLockFacade;
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> createPost(PostCreateRequest request) {
        postLockFacade.createPost(request);
        return BaseResponse.success(PostSuccessCode.POST_CREATED);
    }

    @Override
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPosts(Category category, int page, int size) {
        return BaseResponse.success(PostSuccessCode.POST_FETCHED, postLockFacade.getPosts(category, page, size));
    }

    @Override
    public ResponseEntity<BaseResponse<PostResponse>> getPost(Long postId) {
        return BaseResponse.success(PostSuccessCode.POST_FETCHED, postLockFacade.getPost(postId));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> updatePost(Long postId, PostUpdateRequest request) {
        postLockFacade.updatePost(postId, request);
        return BaseResponse.success(PostSuccessCode.POST_UPDATED);
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> deletePost(Long postId) {
        postLockFacade.deletePost(postId);
        return BaseResponse.success(PostSuccessCode.POST_DELETED);
    }
}
