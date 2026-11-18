package org.sopt.member.controller;

import org.sopt.global.auth.cookie.RefreshTokenCookieProvider;
import org.sopt.global.auth.token.TokenPair;
import org.sopt.global.response.BaseResponse;
import org.sopt.global.auth.AuthMember;
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

    @Override
    public ResponseEntity<BaseResponse<Void>> reissue(String refreshToken) {
        TokenPair tokens = authService.reissue(refreshToken);
        return ResponseEntity.status(MemberSuccessCode.TOKEN_REISSUED.getHttpStatus())
            .headers(createTokenHeaders(tokens))
            .body(BaseResponse.success(MemberSuccessCode.TOKEN_REISSUED));
    }

    @Override
    public ResponseEntity<BaseResponse<Void>> logout(AuthMember authMember) {
        authService.logout(authMember);
        return ResponseEntity.status(MemberSuccessCode.LOGOUT_SUCCESS.getHttpStatus())
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookieProvider.delete().toString())
            .body(BaseResponse.success(MemberSuccessCode.LOGOUT_SUCCESS));
    }

    private HttpHeaders createTokenHeaders(TokenPair tokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokens.accessToken());
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookieProvider.create(tokens.refreshToken()).toString());
        return headers;
    }
}
