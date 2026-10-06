package org.sopt.global.error;

public enum ErrorCode {

    // Post Exception
    EMPTY_TITLE_OR_CONTENT("P001", "제목과 본문은 비어있을 수 없습니다."),
    CATEGORY_NOT_FOUND("P002", "존재하지 않는 카테고리입니다."),
    POST_NOT_FOUND("P003", "존재하지 않는 게시글입니다."),
    POST_EMPTY("P004", "게시글이 없습니다."),
    ASSIGNED_POST("P005", "이미 id가 할당된 게시물입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
