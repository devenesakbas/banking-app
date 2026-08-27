package com.banking.banking_app_backend.card.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class IllegalCardStatusException extends BaseException {
    public IllegalCardStatusException(String message) {
        super(message, ErrorCodes.ILLEGAL_CARD_STATE, HttpStatus.BAD_REQUEST);
    }
}
