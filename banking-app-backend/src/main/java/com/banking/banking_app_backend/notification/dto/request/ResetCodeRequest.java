package com.banking.banking_app_backend.notification.dto.request;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ResetCodeRequest(

        @NotBlank(message = ValidationMessages.NOT_BLANK)
        String to,

        @NotBlank(message = ValidationMessages.NOT_BLANK)
        @Size(min = 6, max = 6, message = ValidationMessages.RESET_CODE_LENGTH)
        String code

) {
}
