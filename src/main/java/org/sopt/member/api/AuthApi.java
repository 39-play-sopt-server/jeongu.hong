package org.sopt.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.sopt.global.jwt.RefreshTokenCookieProvider;
import org.sopt.global.response.BaseResponse;
import org.sopt.global.security.AuthMember;
import org.sopt.member.dto.request.LoginRequest;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth", description = "SOPT Auth API")
@SecurityRequirements // 토큰 없이 호출하는 API
@RequestMapping("api/v1/auth")
public interface AuthApi {

    @Operation(summary = "회원가입 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값입니다.(400)"),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일입니다.(M002)")
    })
    @PostMapping("/signup")
    ResponseEntity<BaseResponse<Long>> signup(@RequestBody @Valid MemberCreateRequest request);

    @Operation(summary = "로그인 API",
            description = "Access Token은 Authorization 헤더, Refresh Token은 HttpOnly 쿠키로 발급됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공 (Authorization 헤더 + refreshToken 쿠키)"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값입니다.(400)"),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호가 올바르지 않습니다.(M003)")
    })
    @PostMapping("/login")
    ResponseEntity<BaseResponse<Void>> login(@RequestBody @Valid LoginRequest request);

    @Operation(summary = "토큰 재발급 API",
        description = "refreshToken 쿠키로 Access/Refresh Token을 재발급합니다. 응답 형식은 로그인과 같습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "재발급 성공 (Authorization 헤더 + refreshToken 쿠키)"),
        @ApiResponse(responseCode = "401", description = "만료(A001), 위조(A002), 세션 없음(A003), 재사용 탐지(A004), 세션 만료(A005)")
    })
    @PostMapping("/reissue")
    ResponseEntity<BaseResponse<Void>> reissue(
        @Parameter(hidden = true)
        @CookieValue(name = RefreshTokenCookieProvider.COOKIE_NAME, required = false) String refreshToken);

    @Operation(summary = "로그아웃 API",
        description = "서버의 Refresh Token 세션을 삭제하고 refreshToken 쿠키를 만료시킵니다.")
    @SecurityRequirement(name = "JWT")   // 클래스의 @SecurityRequirements(토큰 없음)를 이 메서드만 덮어씀
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그아웃 성공 (refreshToken 쿠키 삭제)"),
        @ApiResponse(responseCode = "401", description = "인증 필요(401), 만료(A001), 위조(A002)")
    })
    @PostMapping("/logout")
    ResponseEntity<BaseResponse<Void>> logout(
        @Parameter(hidden = true) @AuthenticationPrincipal AuthMember authMember);
}
