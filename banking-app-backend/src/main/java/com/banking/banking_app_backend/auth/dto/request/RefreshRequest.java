package com.banking.banking_app_backend.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshRequest(

        @NotBlank(message = "{validation.notblank}")
        String refreshToken

) {


}
