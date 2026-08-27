package com.banking.banking_app_backend.account.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends BaseException {
    public IllegalArgumentException(String message) {
        super(message, ErrorCodes.ILLEGAL_ARGUMENT, HttpStatus.BAD_REQUEST);
    }
}
