package com.banking.banking_app_backend.transaction.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class BalanceErrorException extends BaseException {
    public BalanceErrorException(String message) {
        super(message, ErrorCodes.BALANCE_ERROR, HttpStatus.BAD_REQUEST);
    }
}
