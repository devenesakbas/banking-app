package com.banking.banking_app_backend.user.dto.request;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInsertRequest(

    @NotBlank(message = ValidationMessages.NOT_BLANK)
    String name,

    @NotBlank(message = ValidationMessages.NOT_BLANK)
    String surname,

    @NotBlank(message = ValidationMessages.NOT_BLANK)
    @Email(message = ValidationMessages.EMAIL_WELLFORMED)
    String email,

    @NotBlank(message = ValidationMessages.NOT_BLANK)
    @Size(min = 6, message = ValidationMessages.PASSWORD_SIZE)
    String password

){}
