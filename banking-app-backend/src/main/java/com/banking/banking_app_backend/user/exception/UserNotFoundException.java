package com.banking.banking_app_backend.user.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message, ErrorCodes.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }

}
