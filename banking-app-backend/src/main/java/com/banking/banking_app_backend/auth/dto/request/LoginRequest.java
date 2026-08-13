package com.banking.banking_app_backend.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @Email(message = "{validation.email.wellformed}")
        @NotBlank(message = "{validaiton.notblank}")
        String email,

        @NotBlank(message = "{validation.notblank}")
        @Size(min = 6, message = "{validation.password.size}")
        String password
) {
}
