package org.sopt.member.code;

import org.sopt.global.code.SuccessCode;
import org.springframework.http.HttpStatus;

public enum MemberSuccessCode implements SuccessCode {

    MEMBER_CREATED(HttpStatus.CREATED, "회원 생성 성공"),
    MEMBER_DELETED(HttpStatus.OK, "회원 삭제 성공");

    private final HttpStatus httpStatus;
    private final String message;

    MemberSuccessCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
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
}
