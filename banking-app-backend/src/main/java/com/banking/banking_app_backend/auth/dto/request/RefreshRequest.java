package com.banking.banking_app_backend.auth.dto.request;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshRequest(

        @NotBlank(message = ValidationMessages.NOT_BLANK)
        String refreshToken

) {


}
