package com.banking.banking_app_backend.auth.dto.response;

import com.banking.banking_app_backend.user.entity.UserRole;
import lombok.Builder;

@Builder
public record MeResponse(

        String name,
        String surname,
        String email,
        UserRole role

) {
}
