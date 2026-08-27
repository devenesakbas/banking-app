package com.banking.banking_app_backend.account.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InvalidAccountException extends BaseException {
    public InvalidAccountException(String message) {

        super(message, ErrorCodes.INVALID_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
    }
}
