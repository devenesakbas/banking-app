package com.banking.banking_app_backend.user.dto.request;

public record UserUpdateRequest(
        String name,
        String surname,
        String email,
        String password
) {}
