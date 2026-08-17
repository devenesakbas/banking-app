package com.banking.banking_app_backend.auth.dto.request;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ResetPasswordRequest(

        @NotBlank(message = ValidationMessages.NOT_BLANK)
        @Email(message = ValidationMessages.EMAIL_WELLFORMED)
        String email,

        @NotBlank(message = ValidationMessages.NOT_BLANK)
        String password,

        @NotBlank(message = ValidationMessages.NOT_BLANK)
        String passwordReply
) {
}
