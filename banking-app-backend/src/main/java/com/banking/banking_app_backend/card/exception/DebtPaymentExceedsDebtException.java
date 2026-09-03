package com.banking.banking_app_backend.card.exception;

import com.banking.banking_app_backend.common.exception.BaseException;
import com.banking.banking_app_backend.common.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class DebtPaymentExceedsDebtException extends BaseException {
    public DebtPaymentExceedsDebtException(String message) {
        super(message, ErrorCodes.DEBT_PAYMENT_EXCEEDS_DEBT, HttpStatus.BAD_REQUEST);
    }
}
