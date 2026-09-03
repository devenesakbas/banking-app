package com.banking.banking_app_backend.card.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class CreditCardNotFoundException extends BaseException {
    public CreditCardNotFoundException(String message) {
        super(message, ErrorCodes.CREDIT_CARD_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }
}
