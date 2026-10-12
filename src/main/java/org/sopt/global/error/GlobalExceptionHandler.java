package org.sopt.global.error;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.sopt.global.code.ErrorCode;
import org.sopt.global.code.GlobalErrorCode;
import org.sopt.global.response.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Void>> handleServiceException(BusinessException e) {

        return BaseResponse.fail(e.getErrorCode());
    }

    // 요청 본문 검증 실패 (필드별 오류 메시지를 함께 응답)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.putIfAbsent(error.getField(), error.getDefaultMessage()));

        ErrorCode errorCode = GlobalErrorCode.INVALID_REQUEST;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new BaseResponse<>(false, errorCode.getCode(), errorCode.getMessage(), errors));
    }

    // Spring MVC 표준 예외 (상태 코드는 Spring이 정한 값을 그대로 사용하고 본문만 통일)
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception e, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        HttpStatus status = HttpStatus.valueOf(statusCode.value());
        String message = (status == HttpStatus.BAD_REQUEST)
                ? GlobalErrorCode.INVALID_REQUEST.getMessage()
                : status.getReasonPhrase();

        return ResponseEntity
                .status(status)
                .headers(headers)
                .body(new BaseResponse<>(false, String.valueOf(status.value()), message, null));
    }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {

        return BaseResponse.fail(GlobalErrorCode.INTERNAL_ERROR);
    }
}
