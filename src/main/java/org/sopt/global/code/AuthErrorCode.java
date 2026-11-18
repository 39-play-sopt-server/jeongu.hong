package org.sopt.global.code;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {

	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "만료된 토큰입니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰입니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A003", "Refresh Token이 존재하지 않습니다."),
	REFRESH_TOKEN_REUSED(HttpStatus.UNAUTHORIZED, "A004", "이미 사용된 Refresh Token입니다. 다시 로그인해주세요."),
	SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "A005", "로그인 세션이 만료되었습니다. 다시 로그인해주세요."),
	REVOKED_TOKEN(HttpStatus.UNAUTHORIZED, "A006", "무효화된 토큰입니다. 다시 로그인해주세요.");;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	AuthErrorCode(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getCode() {
		return code;
	}
}
