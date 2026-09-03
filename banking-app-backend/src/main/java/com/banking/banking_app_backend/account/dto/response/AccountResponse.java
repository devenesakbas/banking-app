package com.banking.banking_app_backend.account.dto.response;

import com.banking.banking_app_backend.account.entity.AccountCurrency;
import com.banking.banking_app_backend.account.entity.AccountStatus;
import com.banking.banking_app_backend.account.entity.AccountType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AccountResponse(
        Long id,
        Long userId,
        String accountNumber,
        String iban,
        AccountType accountType,
        AccountCurrency accountCurrency,
        BigDecimal balance,
        AccountStatus accountStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
