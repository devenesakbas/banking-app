package com.banking.banking_app_backend.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserInsertRequest(

    @NotBlank(message = "Name cannot be blank")
    String name,

    @NotBlank(message = "Surname cannot be blank")
    String surname,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password

){}
