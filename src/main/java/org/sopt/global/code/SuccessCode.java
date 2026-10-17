package org.sopt.global.code;

import org.springframework.http.HttpStatus;

public interface SuccessCode {

    HttpStatus getHttpStatus();

    String getMessage();

    default String getCode() {
        return String.valueOf(getHttpStatus().value());
    }
}
