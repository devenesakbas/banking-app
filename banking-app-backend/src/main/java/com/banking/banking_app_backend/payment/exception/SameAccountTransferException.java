package com.banking.banking_app_backend.payment.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class SameAccountTransferException extends BaseException {
    public SameAccountTransferException(String message) {
        super(message, ErrorCodes.SAME_ACCOUNT_TRANSFER, HttpStatus.BAD_REQUEST);
    }
}
