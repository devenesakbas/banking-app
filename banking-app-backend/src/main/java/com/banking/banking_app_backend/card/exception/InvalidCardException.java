package com.banking.banking_app_backend.card.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InvalidCardException extends BaseException {
    public InvalidCardException(String message) {
        super(message, ErrorCodes.INVALID_CARD, HttpStatus.BAD_REQUEST);
    }
}
