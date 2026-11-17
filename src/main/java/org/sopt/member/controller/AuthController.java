package org.sopt.member.controller;

import org.sopt.global.jwt.RefreshTokenCookieProvider;
import org.sopt.global.jwt.TokenPair;
import org.sopt.global.response.BaseResponse;
import org.sopt.member.api.AuthApi;
import org.sopt.member.code.MemberSuccessCode;
import org.sopt.member.dto.request.LoginRequest;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    @Override
    public ResponseEntity<BaseResponse<Long>> signup(MemberCreateRequest request) {
        return ResponseEntity.status(MemberSuccessCode.MEMBER_CREATED.getHttpStatus())
                .body(BaseResponse.success(MemberSuccessCode.MEMBER_CREATED, authService.signup(request)));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> login(LoginRequest request) {
        TokenPair tokens = authService.login(request);
        return ResponseEntity.status(MemberSuccessCode.LOGIN_SUCCESS.getHttpStatus())
            .headers(createTokenHeaders(tokens))
            .body(BaseResponse.success(MemberSuccessCode.LOGIN_SUCCESS));
    }

    private HttpHeaders createTokenHeaders(TokenPair tokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokens.accessToken());
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookieProvider.create(tokens.refreshToken()).toString());
        return headers;
    }
}
