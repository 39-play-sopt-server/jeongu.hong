package org.sopt.post.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.response.BaseResponse;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "SOPT Post API")
@RequestMapping(path = "api/v1/posts")
public interface PostApi {

    @Operation(summary = "게시물 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "제목과 본문은 비어있을 수 없습니다.(P001) / 존재하지 않는 카테고리입니다.(P002)")
    })
    @PostMapping
    ResponseEntity<BaseResponse<Void>> createPost(@RequestBody PostCreateRequest request);

    @Operation(summary = "게시물 목록 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글이 없습니다.(P004)")
    })
    @GetMapping
    ResponseEntity<BaseResponse<List<PostResponse>>> getPosts(@RequestParam(name = "page", defaultValue = "1") int page);

    @Operation(summary = "게시물 단건 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.(P003) / 게시글이 없습니다.(P004)")
    })
    @GetMapping(path = "/{postId}")
    ResponseEntity<BaseResponse<PostResponse>> getPost(@PathVariable Long postId);

    @Operation(summary = "게시물 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "제목과 본문은 비어있을 수 없습니다.(P001)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.(P003) / 게시글이 없습니다.(P004)")
    })
    @PutMapping(path = "/{postId}")
    ResponseEntity<BaseResponse<Void>> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request);

    @Operation(summary = "게시물 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.(P003) / 게시글이 없습니다.(P004)")
    })
    @DeleteMapping(path = "/{postId}")
    ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long postId);
}
