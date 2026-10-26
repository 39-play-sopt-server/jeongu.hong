package org.sopt.post.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.global.response.BaseResponse;
import org.sopt.post.domain.Category;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.post.dto.response.PostResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "SOPT Post API")
@RequestMapping(path = "api/v1/posts")
public interface PostApi {

    @Operation(summary = "게시물 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값입니다.(400)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원입니다.(M001)")
    })
    @PostMapping
    ResponseEntity<BaseResponse<Void>> createPost(
            @RequestParam Long memberId,
            @RequestBody @Valid PostCreateRequest request
    );

    @Operation(summary = "게시물 목록 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공")
    })
    @GetMapping
    ResponseEntity<BaseResponse<Page<PostResponse>>> getPosts(
            @RequestParam(name = "category", required = false) Category category,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "memberId", required = false) Long memberId,
            @ParameterObject @PageableDefault(sort = {"createdAt", "id"}, direction = Sort.Direction.DESC) Pageable pageable
    );

    @Operation(summary = "게시물 단건 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.(P003)")
    })
    @GetMapping(path = "/{postId}")
    ResponseEntity<BaseResponse<PostResponse>> getPost(@PathVariable Long postId);

    @Operation(summary = "게시물 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값입니다.(400)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.(P003)")
    })
    @PutMapping(path = "/{postId}")
    ResponseEntity<BaseResponse<Void>> updatePost(@PathVariable Long postId, @RequestBody @Valid PostUpdateRequest request);

    @Operation(summary = "게시물 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.(P003)")
    })
    @DeleteMapping(path = "/{postId}")
    ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long postId);
}
