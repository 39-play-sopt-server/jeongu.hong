package org.sopt.post.controller;

import org.sopt.global.response.BaseResponse;
import org.sopt.global.response.PageResponse;
import org.sopt.global.auth.AuthMember;
import org.sopt.post.api.PostApi;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.domain.Category;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;
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
    public ResponseEntity<BaseResponse<Void>> createPost(AuthMember authMember, PostCreateRequest request) {
        postService.createPost(authMember.memberId(), request);
        return ResponseEntity.status(PostSuccessCode.POST_CREATED.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.POST_CREATED));
    }

    @Override
    public ResponseEntity<BaseResponse<PageResponse<PostResponse>>> getPosts(Category category, String keyword, Long memberId, Pageable pageable) {
        return ResponseEntity.status(PostSuccessCode.POST_FETCHED.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.POST_FETCHED, postService.getPosts(category, keyword, memberId, pageable)));
    }

    @Override
    public ResponseEntity<BaseResponse<PostResponse>> getPost(Long postId) {
        return ResponseEntity.status(PostSuccessCode.POST_FETCHED.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.POST_FETCHED, postService.getPost(postId)));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> updatePost(AuthMember authMember, Long postId, PostUpdateRequest request) {
        postService.updatePost(authMember.memberId(), postId, request);
        return ResponseEntity.status(PostSuccessCode.POST_UPDATED.getHttpStatus())
            .body(BaseResponse.success(PostSuccessCode.POST_UPDATED));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> deletePost(AuthMember authMember, Long postId) {
        postService.deletePost(authMember.memberId(), postId);
        return ResponseEntity.status(PostSuccessCode.POST_DELETED.getHttpStatus())
            .body(BaseResponse.success(PostSuccessCode.POST_DELETED));
    }
}
