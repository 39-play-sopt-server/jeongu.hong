package org.sopt.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.sopt.global.response.BaseResponse;
import org.sopt.global.auth.AuthMember;
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
	ResponseEntity<BaseResponse<Long>> me(@Parameter(hidden = true) @AuthenticationPrincipal AuthMember authMember);

	@Operation(summary = "회원 탈퇴 API",
		description = "로그인한 본인 계정을 탈퇴 처리하고, Refresh Token 세션과 쿠키를 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "회원 탈퇴 성공 (refreshToken 쿠키 삭제)"),
		@ApiResponse(responseCode = "401", description = "인증 필요(401), 만료(A001), 위조(A002)"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 회원입니다.(M001)")
	})
	@DeleteMapping("/me")
	ResponseEntity<BaseResponse<Void>> withdraw(
		@Parameter(hidden = true) @AuthenticationPrincipal AuthMember authMember);
}
