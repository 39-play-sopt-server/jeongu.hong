package org.sopt.global.auth.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.sopt.global.code.ErrorCode;
import org.sopt.global.response.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class SecurityErrorResponder {

	private final ObjectMapper objectMapper;

	public void respond(HttpServletResponse response, ErrorCode errorCode) throws IOException {
		response.setStatus(errorCode.getHttpStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		objectMapper.writeValue(response.getWriter(), BaseResponse.fail(errorCode));
	}
}