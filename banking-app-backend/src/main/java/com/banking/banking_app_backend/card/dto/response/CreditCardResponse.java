package com.banking.banking_app_backend.card.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CreditCardResponse(

        BigDecimal creditLimit,

        BigDecimal currentDebt,

        BigDecimal availableCredit,

        BigDecimal minimumPayment,

        LocalDate dueDate

) {
}
