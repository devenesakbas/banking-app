package com.banking.banking_app_backend.card.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InsufficientCreditLimitException extends BaseException {
    public InsufficientCreditLimitException(String message) {
        super(message, ErrorCodes.INSUFFICIENT_CREDIT_LIMIT, HttpStatus.BAD_REQUEST);
    }
}
