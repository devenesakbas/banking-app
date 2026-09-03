package com.banking.banking_app_backend.payment.dto.request;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentCreditCardDebtPaymentRequest(

        @NotNull(message = ValidationMessages.NOT_NULL)
        Long cardId,

        @NotNull(message = ValidationMessages.NOT_NULL)
        Long sourceAccountId,

        @NotNull(message = ValidationMessages.NOT_NULL)
        @DecimalMin("0.01")
        BigDecimal amount

) {
}
