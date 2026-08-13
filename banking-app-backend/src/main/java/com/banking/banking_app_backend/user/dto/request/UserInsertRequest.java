package com.banking.banking_app_backend.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInsertRequest(

    @NotBlank(message = "{validation.notblank}")
    String name,

    @NotBlank(message = "{validation.notblank}")
    String surname,

    @NotBlank(message = "{validation.notblank}")
    @Email(message = "{validation.email.wellformed}")
    String email,

    @NotBlank(message = "{validation.notblank}")
    @Size(min = 6, message = "{validation.password.size}")
    String password

){}
