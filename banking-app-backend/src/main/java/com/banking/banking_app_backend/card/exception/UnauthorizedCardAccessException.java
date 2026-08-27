package com.banking.banking_app_backend.card.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class UnauthorizedCardAccessException extends BaseException {
    public UnauthorizedCardAccessException(String message) {
        super(message, ErrorCodes.UNAUTHORIZED_CARD_EXCEPTION, HttpStatus.BAD_REQUEST);
    }
}
