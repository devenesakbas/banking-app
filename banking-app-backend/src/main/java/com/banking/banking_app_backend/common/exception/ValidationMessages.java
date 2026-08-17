package com.banking.banking_app_backend.common.exception;

public final class ValidationMessages {

    private ValidationMessages() {}

    public static final String NOT_BLANK = "Bu alan boş bırakılamaz.";
    public static final String EMAIL_WELLFORMED = "Lütfen geçerli ve düzgün biçimli bir e-posta adresi giriniz.";
    public static final String PASSWORD_SIZE = "Şifreniz en az 6 karakterden oluşmalıdır.";
    public static final String RESET_CODE_LENGTH = "Reset kodunuz 6 karakterden oluşmalıdır.";

}
