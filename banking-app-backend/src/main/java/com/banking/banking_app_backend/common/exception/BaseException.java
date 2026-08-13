package com.banking.banking_app_backend.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus httpStatus;

    public BaseException(String message, String errorCode, HttpStatus httpStatus){
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

}
