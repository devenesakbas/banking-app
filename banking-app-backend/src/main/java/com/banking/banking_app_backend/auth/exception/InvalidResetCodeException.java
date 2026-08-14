package com.banking.banking_app_backend.auth.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InvalidResetCodeException extends BaseException {
    public InvalidResetCodeException(String message) {

      super(message, ErrorCodes.INVALID_RESET_CODE, HttpStatus.BAD_REQUEST);
    }
}
