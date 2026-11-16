package org.sopt.member.controller;

import org.sopt.global.response.BaseResponse;
import org.sopt.member.api.MemberApi;
import org.sopt.member.code.MemberSuccessCode;
import org.sopt.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController implements MemberApi {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public ResponseEntity<BaseResponse<Long>> me(Long memberId) {
        return BaseResponse.success(MemberSuccessCode.MEMBER_FETCHED, memberId);
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> deleteMember(Long memberId) {
        memberService.deleteMember(memberId);
        return BaseResponse.success(MemberSuccessCode.MEMBER_DELETED);
    }
}
