package org.sopt.global.auth.web;

import static org.sopt.global.auth.web.TokenAuthenticationFilter.*;

import java.io.IOException;

import org.sopt.global.code.ErrorCode;
import org.sopt.global.code.GlobalErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// 인증 실패 시 401 + 공통 응답(BaseResponse) 형식으로 응답
@Component
@RequiredArgsConstructor
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final SecurityErrorResponder errorResponder;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        errorResponder.respond(response, resolveErrorCode(request));
    }


    private ErrorCode resolveErrorCode(HttpServletRequest request) {
        Object errorCode = request.getAttribute(ERROR_CODE_ATTRIBUTE);
        if (errorCode instanceof ErrorCode code) {
            return code;
        }
        return GlobalErrorCode.UNAUTHORIZED;
    }
}
