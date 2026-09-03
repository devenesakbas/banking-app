package com.banking.banking_app_backend.payment.dto.request;

import com.banking.banking_app_backend.account.entity.AccountCurrency;
import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentWithdrawRequest(

        Long accountId,

        @NotNull(message = ValidationMessages.NOT_NULL)
        @DecimalMin(
                value = "0.01",
                message = ValidationMessages.GREATER_THAN_ZERO
        )
        BigDecimal amount,

        @NotNull(message = ValidationMessages.NOT_NULL)

        AccountCurrency currency,
        String description
) {
}
