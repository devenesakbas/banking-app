package com.banking.banking_app_backend.auth.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class EmailSendException extends BaseException {
    public EmailSendException(String message) {

        super(message, ErrorCodes.EMAIL_SEND_ERROR, HttpStatus.BAD_REQUEST);
    }
}
