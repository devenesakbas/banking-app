package com.banking.banking_app_backend.auth.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @NotBlank(message = "{validaiton.notblank}")
        String name,

        @NotBlank(message = "{validation.notblank}")
        String surname,

        @Email(message = "{validation.email.wellformed}")
        @NotBlank(message = "{validaiton.notBlank}")
        String email,

        @NotBlank(message = "{validation.notblank}")
        String password
) {
}
