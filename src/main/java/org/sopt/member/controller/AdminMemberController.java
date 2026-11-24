package org.sopt.member.controller;

import org.sopt.global.response.BaseResponse;
import org.sopt.global.response.PageResponse;
import org.sopt.member.api.AdminMemberApi;
import org.sopt.member.code.MemberSuccessCode;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminMemberController implements AdminMemberApi {

	private final MemberService memberService;

	@Override
	public ResponseEntity<BaseResponse<PageResponse<MemberResponse>>> getMembers(Pageable pageable) {
		return ResponseEntity.status(MemberSuccessCode.MEMBERS_FETCHED.getHttpStatus())
			.body(BaseResponse.success(MemberSuccessCode.MEMBERS_FETCHED, memberService.getMembers(pageable)));
	}
}
