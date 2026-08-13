package com.banking.banking_app_backend.auth.dto.request;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(

        @NotBlank(message = ValidationMessages.NOT_BLANK)
        @Email(message = ValidationMessages.EMAIL_WELLFORMED)
        String email

) {
}
