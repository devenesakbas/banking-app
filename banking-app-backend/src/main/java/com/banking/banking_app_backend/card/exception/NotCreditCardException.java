package com.banking.banking_app_backend.card.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class NotCreditCardException extends BaseException {
    public NotCreditCardException(String message) {
        super(message, ErrorCodes.NOT_CREDIT_CARD, HttpStatus.BAD_REQUEST);
    }
}
