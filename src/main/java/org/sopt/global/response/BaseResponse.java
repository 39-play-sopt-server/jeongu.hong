package org.sopt.global.response;

import org.sopt.global.code.SuccessCode;
import org.sopt.global.code.ErrorCode;

// 응답 body만 담당한다. 상태 코드와 헤더는 ResponseEntity를 조립하는 쪽에서 정한다.
public record BaseResponse<T> (boolean success, String code, String message, T data) {

    public static <T> BaseResponse<T> success(SuccessCode successCode, T data) {
        return new BaseResponse<>(true, successCode.getCode(), successCode.getMessage(), data);
    }

    public static BaseResponse<Void> success(SuccessCode successCode) {
        return success(successCode, null);
    }

    public static BaseResponse<Void> fail(ErrorCode errorCode) {
        return new BaseResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
    }
}
