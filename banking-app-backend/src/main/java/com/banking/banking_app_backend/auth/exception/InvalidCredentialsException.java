package com.banking.banking_app_backend.auth.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BaseException {

    public InvalidCredentialsException(String message){
        super(message, ErrorCodes.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }

}
