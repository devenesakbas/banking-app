package com.banking.banking_app_backend.auth.dto.response;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record VerifyResetCodeResponse(

        @NotBlank(message = ValidationMessages.NOT_BLANK)
        boolean verify

) {
}
