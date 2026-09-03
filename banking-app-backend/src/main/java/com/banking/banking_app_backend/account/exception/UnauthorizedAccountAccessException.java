package com.banking.banking_app_backend.account.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class UnauthorizedAccountAccessException extends BaseException {
    public UnauthorizedAccountAccessException(String message) {
        super(message, ErrorCodes.UNAUTHORIZED_ACCOUNT, HttpStatus.FORBIDDEN);
    }
}
