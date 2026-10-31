package org.sopt.post.code;

import org.sopt.global.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {

    EMPTY_TITLE(HttpStatus.BAD_REQUEST, "P001", "제목은 비어있을 수 없습니다."),
    EMPTY_CATEGORY(HttpStatus.BAD_REQUEST, "P002", "카테고리는 비어있을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P003", "존재하지 않는 게시글입니다."),
    EMPTY_CONTENT(HttpStatus.BAD_REQUEST, "P004", "본문은 비어있을 수 없습니다."),
    ASSIGNED_POST(HttpStatus.INTERNAL_SERVER_ERROR, "P005", "이미 id가 할당된 게시물입니다."),
    EMPTY_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, "P006", "게시글 작성자는 비어있을 수 없습니다."),
    EMPTY_LIKE_POST(HttpStatus.INTERNAL_SERVER_ERROR, "P007", "좋아요 대상 게시글은 비어있을 수 없습니다."),
    EMPTY_LIKE_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, "P008", "좋아요를 누른 회원은 비어있을 수 없습니다."),
    ALREADY_LIKED(HttpStatus.CONFLICT, "P009", "이미 좋아요를 누른 게시글입니다."),
    NOT_LIKED(HttpStatus.NOT_FOUND, "P010", "좋아요를 누르지 않은 게시글입니다.");

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
