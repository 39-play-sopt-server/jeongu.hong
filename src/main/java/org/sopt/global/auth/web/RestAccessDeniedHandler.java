package org.sopt.global.auth.web;

import java.io.IOException;

import org.sopt.global.code.GlobalErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// 인증은 됐지만 권한이 부족할 때(예: USER가 관리자 API 호출) 403 응답
@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	private final SecurityErrorResponder errorResponder;

	@Override
	public void handle(HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		errorResponder.respond(response, GlobalErrorCode.FORBIDDEN);
	}
}