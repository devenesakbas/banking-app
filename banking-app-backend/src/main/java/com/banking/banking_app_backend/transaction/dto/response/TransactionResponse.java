package com.banking.banking_app_backend.transaction.dto.response;

import com.banking.banking_app_backend.transaction.entity.TransactionCurrency;
import com.banking.banking_app_backend.transaction.entity.TransactionStatus;
import com.banking.banking_app_backend.transaction.entity.TransactionType;
import lombok.Builder;
import org.apache.catalina.util.Strftime;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(
        Long id,
        Long accountId,
        TransactionType transactionType,
        BigDecimal amount,
        TransactionCurrency currency,
        TransactionStatus transactionStatus,
        String referenceNumber,
        String description,
        LocalDateTime createdAt
) {
}
