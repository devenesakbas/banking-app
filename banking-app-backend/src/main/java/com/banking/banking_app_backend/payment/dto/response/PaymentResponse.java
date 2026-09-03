package com.banking.banking_app_backend.payment.dto.response;

import com.banking.banking_app_backend.account.entity.AccountCurrency;
import com.banking.banking_app_backend.transaction.entity.TransactionStatus;
import com.banking.banking_app_backend.transaction.entity.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentResponse(

        Long accountId,
        TransactionType transactionType,
        BigDecimal amount,
        AccountCurrency currency,
        TransactionStatus transactionStatus,
        String referenceNumber,
        String description,
        LocalDateTime createdAt

) {
}
