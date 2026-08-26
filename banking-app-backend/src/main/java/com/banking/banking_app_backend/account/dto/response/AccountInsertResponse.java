package com.banking.banking_app_backend.account.dto.response;

import com.banking.banking_app_backend.account.entity.AccountCurrency;
import com.banking.banking_app_backend.account.entity.AccountStatus;
import com.banking.banking_app_backend.account.entity.AccountType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountInsertResponse(
        Long id,
        String accountNumber,
        String iban,
        AccountType accountType,
        AccountCurrency accountCurrency,
        BigDecimal balance,
        AccountStatus accountStatus
) {
}
