package org.sopt.member.controller;

import org.sopt.global.response.BaseResponse;
import org.sopt.member.api.AuthApi;
import org.sopt.member.code.MemberSuccessCode;
import org.sopt.member.dto.request.LoginRequest;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.LoginResponse;
import org.sopt.member.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<BaseResponse<Long>> signup(MemberCreateRequest request) {
        return BaseResponse.success(MemberSuccessCode.MEMBER_CREATED, authService.signup(request));
    }

    @Override
    public ResponseEntity<BaseResponse<LoginResponse>> login(LoginRequest request) {
        return BaseResponse.success(MemberSuccessCode.LOGIN_SUCCESS, authService.login(request));
    }
}
