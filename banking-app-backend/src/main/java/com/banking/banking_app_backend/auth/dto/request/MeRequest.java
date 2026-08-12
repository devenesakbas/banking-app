package com.banking.banking_app_backend.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MeRequest(

        @NotBlank(message = "Access token is required.")
        String accessToken

) {
}
