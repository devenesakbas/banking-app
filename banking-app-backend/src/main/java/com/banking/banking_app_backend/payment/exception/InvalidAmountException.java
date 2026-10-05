package com.banking.banking_app_backend.payment.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InvalidAmountException extends BaseException {
    public InvalidAmountException(String message) {
        super(message, ErrorCodes.INVALID_AMOUNT, HttpStatus.BAD_REQUEST);
    }
}
