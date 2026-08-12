package com.banking.banking_app_backend.auth.dto.response;

public record RegisterResponse(

        Long id,
        String name,
        String surname,
        String email,
        String createdAt

) {
}
