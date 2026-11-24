package org.sopt.member.api;

import org.sopt.global.response.BaseResponse;
import org.sopt.global.response.PageResponse;
import org.sopt.member.dto.response.MemberResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin", description = "관리자 전용 API (ROLE_ADMIN)")
@RequestMapping("api/v1/admin/members")
public interface AdminMemberApi {

	@Operation(summary = "전체 회원 목록 조회 API", description = "탈퇴 회원을 포함한 전체 회원을 조회합니다. 관리자만 호출할 수 있습니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "회원 목록 조회 성공"),
		@ApiResponse(responseCode = "401", description = "인증 필요(401), 만료(A001), 위조(A002), 무효화(A006)"),
		@ApiResponse(responseCode = "403", description = "접근 권한이 없습니다.(403)")
	})
	@GetMapping
	ResponseEntity<BaseResponse<PageResponse<MemberResponse>>> getMembers(
		@ParameterObject @PageableDefault(sort = {"createdAt", "id"}, direction = Sort.Direction.DESC) Pageable pageable);
}
