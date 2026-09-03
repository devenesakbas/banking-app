package com.banking.banking_app_backend.payment.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class CreditCardMinimumPaymentException extends BaseException {
    public CreditCardMinimumPaymentException(String message) {
        super(message, ErrorCodes.CREDIT_CARD_MINIMUM_PAYMENT, HttpStatus.BAD_REQUEST);
    }
}
