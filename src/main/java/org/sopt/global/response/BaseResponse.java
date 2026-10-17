package org.sopt.global.response;

import org.sopt.global.code.SuccessCode;
import org.sopt.global.code.ErrorCode;
import org.springframework.http.ResponseEntity;

public record BaseResponse<T> (boolean success, String code, String message, T data) {

    public static <T> ResponseEntity<BaseResponse<T>> success(SuccessCode successCode, T data) {
        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(new BaseResponse<>(true, successCode.getCode(), successCode.getMessage(), data));
    }

    public static ResponseEntity<BaseResponse<Void>> success(SuccessCode successCode) {
        return success(successCode, null);
    }

    public static ResponseEntity<BaseResponse<Void>> fail(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new BaseResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null));
    }
}
