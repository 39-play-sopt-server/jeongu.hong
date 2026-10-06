package org.sopt.global.code;

public enum SuccessCode implements ResponseCode {

    OK("200", "게시글 조회 성공"),
    CREATED("201", "정상적으로 생성되었습니다."),
    UPDATED("200", "게시글 수정 성공"),
    DELETED("200", "게시글 삭제 성공");

    private final String code;
    private final String message;

    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
