package com.banking.banking_app_backend.transaction.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class UnauthorizedTransactionAccessException extends BaseException {
    public UnauthorizedTransactionAccessException(String message) {
        super(message, ErrorCodes.UNAUTHORIZED_TRANSACTION, HttpStatus.FORBIDDEN);
    }
}
