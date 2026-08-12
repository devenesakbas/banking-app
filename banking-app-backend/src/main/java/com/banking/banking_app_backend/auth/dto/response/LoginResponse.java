package com.banking.banking_app_backend.auth.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LoginResponse(

        String accessToken,
        String refreshToken,
        Long expiresIn

) {
}
