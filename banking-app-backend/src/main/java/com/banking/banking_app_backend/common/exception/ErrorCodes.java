package com.banking.banking_app_backend.common.exception;


public final class ErrorCodes {

    private ErrorCodes() {}

    public static final String EMAIL_ALREADY_EXIST = "EMAIL_ALREADY_EXIST";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String INVALID_TOKEN = "INVALID_TOKEN";
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String EXPIRED_TOKEN = "EXPIRED_TOKEN";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String INVALID_RESET_CODE = "INVALID_RESET_CODE";
    public static final String EMAIL_SEND_ERROR = "EMAIL_SEND_ERROR";
    public static final String PASSWORD_MISMATCH = "PASSWORD_MISMATCH";
    public static final String ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";
    public static final String INVALID_ACCOUNT_NUMBER = "INVALID_ACCOUNT_NUMBER";
    public static final String UNAUTHORIZED_ACCOUNT = "UNAUTHORIZED_ACCOUNT";
    public static final String ILLEGAL_ACCOUNT_STATE = "ILLEGAL_ACCOUNT_STATE";
    public static final String TRANSACTION_NOT_FOUND = "TRANSACTION_NOT_FOUND";
    public static final String UNAUTHORIZED_TRANSACTION = "UNAUTHORIZED_TRANSACTION";
    public static final String ILLEGAL_ARGUMENT = "ILLEGAL_ARGUMENT";
    public static final String BALANCE_ERROR = "BALANCE_ERROR";
    public static final String CARD_NOT_FOUND = "CARD_NOT_FOUND";
    public static final String UNAUTHORIZED_CARD = "UNAUTHORIZED_CARD";
    public static final String ILLEGAL_CARD_STATE = "ILLEGAL_CARD_STATE";
    public static final String INVALID_CARD = "INVALID_CARD";
    public static final String NOT_CREDIT_CARD = "NOT_CREDIT_CARD";
    public static final String INSUFFICIENT_CREDIT_LIMIT = "INSUFFICIENT_CREDIT_LIMIT";
    public static final String DEBT_PAYMENT_EXCEEDS_DEBT = "DEBT_PAYMENT_EXCEEDS_DEBT";
    public static final String CREDIT_CARD_NOT_FOUND = "CREDIT_CARD_NOT_FOUND";
    public static final String CREDIT_CARD_MINIMUM_PAYMENT = "CREDIT_CARD_MINIMUM_PAYMENT";
    public static final String SAME_ACCOUNT_TRANSFER = "SAME_ACCOUNT_TRANSFER";

}
