package com.banking.banking_app_backend.auth.dto.response;

import lombok.Builder;

@Builder
public record ForgotPasswordResponse(

        boolean send

) {
}
