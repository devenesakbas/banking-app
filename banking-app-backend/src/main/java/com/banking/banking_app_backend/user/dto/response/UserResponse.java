package com.banking.banking_app_backend.user.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String surname,
        String email,
        LocalDateTime createdAt
) {
}
