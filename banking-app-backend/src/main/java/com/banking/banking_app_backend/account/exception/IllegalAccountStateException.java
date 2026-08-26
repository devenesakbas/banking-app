package com.banking.banking_app_backend.account.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class IllegalAccountStateException extends BaseException {
    public IllegalAccountStateException(String message) {
        super(message, ErrorCodes.ILLEGAL_ACCOUNT_STATE, HttpStatus.BAD_REQUEST);
    }
}
