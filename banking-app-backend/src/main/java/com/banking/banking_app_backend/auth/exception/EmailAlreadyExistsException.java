package com.banking.banking_app_backend.auth.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends BaseException {

    public EmailAlreadyExistsException(String message) {
        super(message, ErrorCodes.EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
    }
}
