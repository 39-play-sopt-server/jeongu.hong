package org.sopt.global.response;

import org.sopt.global.code.SuccessCode;
import org.sopt.global.code.ErrorCode;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T> (boolean success, String code, String message, T data) {

    public static <T> ResponseEntity<ApiResponse<T>> success(SuccessCode successCode, T data) {
        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), data));
    }

    public static ResponseEntity<ApiResponse<Void>> success(SuccessCode successCode) {
        return success(successCode, null);
    }

    public static ResponseEntity<ApiResponse<Void>> fail(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null));
    }
}
