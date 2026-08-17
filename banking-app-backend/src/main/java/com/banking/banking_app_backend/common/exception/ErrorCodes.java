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

}
