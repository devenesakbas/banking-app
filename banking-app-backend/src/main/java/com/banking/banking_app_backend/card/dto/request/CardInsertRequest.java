package com.banking.banking_app_backend.card.dto.request;

import com.banking.banking_app_backend.card.entity.CardType;
import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.NotNull;


public record CardInsertRequest(

        @NotNull(message = ValidationMessages.NOT_NULL)
        Long accountId,

        @NotNull(message = ValidationMessages.NOT_NULL)
        CardType cardType
) {
}
