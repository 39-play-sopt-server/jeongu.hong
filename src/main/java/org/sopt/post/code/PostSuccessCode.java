package org.sopt.post.code;

import org.sopt.global.code.SuccessCode;
import org.springframework.http.HttpStatus;

public enum PostSuccessCode implements SuccessCode {

    POST_CREATED(HttpStatus.CREATED, "게시글 생성 성공"),
    POST_FETCHED(HttpStatus.OK, "게시글 조회 성공"),
    POST_UPDATED(HttpStatus.OK, "게시글 수정 성공"),
    POST_DELETED(HttpStatus.OK, "게시글 삭제 성공"),
    POST_LIKED(HttpStatus.CREATED, "게시글 좋아요 성공"),
    POST_UNLIKED(HttpStatus.OK, "게시글 좋아요 취소 성공");

    private final HttpStatus httpStatus;
    private final String message;

    PostSuccessCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
