package org.sopt.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "SOPT Member API")
@RequestMapping("api/v1/members")
public interface MemberApi {

    @Operation(summary = "내 정보 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증이 필요합니다.(401)")
    })
    @GetMapping("/me")
    ResponseEntity<BaseResponse<Long>> me(@Parameter(hidden = true) @AuthenticationPrincipal Long memberId);

    @Operation(summary = "회원 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원입니다.(M001)")
    })
    @DeleteMapping("/{memberId}")
    ResponseEntity<BaseResponse<Void>> deleteMember(@PathVariable Long memberId);
}
