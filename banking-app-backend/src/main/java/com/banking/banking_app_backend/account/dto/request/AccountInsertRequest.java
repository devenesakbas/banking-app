package com.banking.banking_app_backend.account.dto.request;

import com.banking.banking_app_backend.account.entity.AccountCurrency;
import com.banking.banking_app_backend.account.entity.AccountType;
import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AccountInsertRequest(

        @NotNull(message = ValidationMessages.NOT_NULL)
        @Enumerated(EnumType.STRING)
        AccountType accountType,

        @NotNull(message = ValidationMessages.NOT_NULL)
        @Enumerated(EnumType.STRING)
        AccountCurrency accountCurrency
) {
}
