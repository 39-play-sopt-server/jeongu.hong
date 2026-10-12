package org.sopt.post.code;

import org.sopt.global.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {

    EMPTY_TITLE_OR_CONTENT(HttpStatus.BAD_REQUEST, "P001", "제목과 본문은 비어있을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P003", "존재하지 않는 게시글입니다."),
    ASSIGNED_POST(HttpStatus.INTERNAL_SERVER_ERROR, "P005", "이미 id가 할당된 게시물입니다."),
    INVALID_PAGE_OR_SIZE(HttpStatus.BAD_REQUEST, "P006", "잘못된 page, size 크기 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    PostErrorCode(HttpStatus httpStatus, String code, String message) {
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
