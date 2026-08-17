package com.banking.banking_app_backend.auth.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends BaseException {
    public PasswordMismatchException(String message) {

        super(message, ErrorCodes.PASSWORD_MISMATCH, HttpStatus.BAD_REQUEST);
    }
}
