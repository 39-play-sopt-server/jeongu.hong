package org.sopt.global.error;

import org.sopt.global.code.ErrorCode;
import org.sopt.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Void>> handleServiceException(BusinessException e) {

        return BaseResponse.fail(e.getErrorCode());
    }

    // 요청 값의 타입 변환 실패 (쿼리 파라미터, 요청 본문)
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<BaseResponse<Void>> handleInvalidRequestException(Exception e) {

        return BaseResponse.fail(ErrorCode.INVALID_REQUEST);
    }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {

        return BaseResponse.fail(ErrorCode.INTERNAL_ERROR);
    }
}
