package org.sopt.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.global.response.BaseResponse;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "SOPT Member API")
@RequestMapping("api/v1/members")
public interface MemberApi {

    @Operation(summary = "회원 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값입니다.(400)"),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일입니다.(M002)")
    })
    @PostMapping
    ResponseEntity<BaseResponse<Void>> createMember(
            @RequestBody @Valid MemberCreateRequest memberCreateRequest
    );

    @Operation(summary = "회원 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원입니다.(M001)")
    })
    @DeleteMapping("/{memberId}")
    ResponseEntity<BaseResponse<Void>> deleteMember(@PathVariable Long memberId);
}
