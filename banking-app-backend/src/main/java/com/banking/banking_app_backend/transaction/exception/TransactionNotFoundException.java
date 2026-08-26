package com.banking.banking_app_backend.transaction.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class TransactionNotFoundException extends BaseException {
    public TransactionNotFoundException(String message) {
        super(message, ErrorCodes.TRANSACTION_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
