package org.sopt.post.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PostLike", description = "SOPT Post Like API")
@RequestMapping(path = "api/v1/posts")
public interface PostLikeApi {

    @Operation(summary = "게시물 좋아요 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 좋아요 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.(P003) / 존재하지 않는 회원입니다.(M001)"),
            @ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시글입니다.(P009)")
    })
    @PostMapping(path = "/{postId}/likes")
    ResponseEntity<BaseResponse<Void>> likePost(
            @RequestHeader(name = "X-Member-Id") Long memberId,
            @PathVariable Long postId
    );

    @Operation(summary = "게시물 좋아요 취소 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 취소 성공"),
            @ApiResponse(responseCode = "404", description = "좋아요를 누르지 않은 게시글입니다.(P010)")
    })
    @DeleteMapping(path = "/{postId}/likes")
    ResponseEntity<BaseResponse<Void>> unlikePost(
            @RequestHeader(name = "X-Member-Id") Long memberId,
            @PathVariable Long postId
    );
}
