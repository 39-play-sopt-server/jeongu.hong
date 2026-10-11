package org.sopt.global.code;

import org.springframework.http.HttpStatus;

public enum SuccessCode {

    // Post
    POST_CREATED(HttpStatus.CREATED, "게시글 생성 성공"),
    POST_FETCHED(HttpStatus.OK, "게시글 조회 성공"),
    POST_UPDATED(HttpStatus.OK, "게시글 수정 성공"),
    POST_DELETED(HttpStatus.OK, "게시글 삭제 성공");

    private final HttpStatus httpStatus;
    private final String message;

    SuccessCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getCode() {
        return String.valueOf(this.httpStatus.value());
    }

    public String getMessage() {
        return this.message;
    }
}
