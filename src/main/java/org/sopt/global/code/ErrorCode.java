package org.sopt.global.code;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Post Exception
    EMPTY_TITLE_OR_CONTENT(HttpStatus.BAD_REQUEST, "P001", "제목과 본문은 비어있을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "P002", "존재하지 않는 카테고리입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P003", "존재하지 않는 게시글입니다."),
    POST_EMPTY(HttpStatus.NOT_FOUND, "P004", "게시글이 없습니다."),
    ASSIGNED_POST(HttpStatus.INTERNAL_SERVER_ERROR, "P005", "이미 id가 할당된 게시물입니다."),
    INVALID_PAGE_OR_SIZE(HttpStatus.BAD_REQUEST, "P006", "잘못된 page, size 크기 요청입니다."),

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 내부 오류입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
