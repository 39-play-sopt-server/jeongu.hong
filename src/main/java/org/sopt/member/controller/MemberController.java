package org.sopt.member.controller;

import org.sopt.global.jwt.RefreshTokenCookieProvider;
import org.sopt.global.response.BaseResponse;
import org.sopt.global.security.AuthMember;
import org.sopt.member.api.MemberApi;
import org.sopt.member.code.MemberSuccessCode;
import org.sopt.member.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    @Override
    public ResponseEntity<BaseResponse<Long>> me(AuthMember authMember) {
        return ResponseEntity.status(MemberSuccessCode.MEMBER_FETCHED.getHttpStatus())
                .body(BaseResponse.success(MemberSuccessCode.MEMBER_FETCHED, authMember.memberId()));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> withdraw(AuthMember authMember) {
        memberService.withdraw(authMember.memberId());
        return ResponseEntity.status(MemberSuccessCode.MEMBER_DELETED.getHttpStatus())
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookieProvider.delete().toString())
            .body(BaseResponse.success(MemberSuccessCode.MEMBER_DELETED));
    }
}
