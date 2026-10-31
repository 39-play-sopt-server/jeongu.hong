package org.sopt.post.controller;

import org.sopt.global.response.BaseResponse;
import org.sopt.post.api.PostLikeApi;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.service.PostLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostLikeController implements PostLikeApi {

    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> likePost(Long memberId, Long postId) {
        postLikeService.likePost(memberId, postId);
        return BaseResponse.success(PostSuccessCode.POST_LIKED);
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> unlikePost(Long memberId, Long postId) {
        postLikeService.unlikePost(memberId, postId);
        return BaseResponse.success(PostSuccessCode.POST_UNLIKED);
    }
}
