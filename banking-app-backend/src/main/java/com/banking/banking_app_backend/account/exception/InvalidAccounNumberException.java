package com.banking.banking_app_backend.account.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InvalidAccounNumberException extends BaseException {
    public InvalidAccounNumberException(String message) {

        super(message, ErrorCodes.INVALID_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
    }
}
