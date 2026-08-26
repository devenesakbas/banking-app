package com.banking.banking_app_backend.account.util;

import com.banking.banking_app_backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {

    private final AccountRepository accountRepository;

    public String generate() {
        String accountNumber;

        do {
            Long randomNumber = ThreadLocalRandom.current()
                    .nextLong(100_000_000_000_000L,
                            1_000_000_000_000_000L);

            accountNumber = String.valueOf(randomNumber);
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

}
