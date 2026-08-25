package com.banking.banking_app_backend.account.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException(String message) {
        super(message, ErrorCodes.ACCOUNT_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }
}
