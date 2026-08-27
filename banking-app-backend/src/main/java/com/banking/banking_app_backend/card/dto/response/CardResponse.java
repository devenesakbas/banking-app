package com.banking.banking_app_backend.card.dto.response;

import com.banking.banking_app_backend.card.entity.CardStatus;
import com.banking.banking_app_backend.card.entity.CardType;

import java.time.LocalDateTime;
import java.time.YearMonth;

public record CardResponse(

        Long id,

        Long accountId,

        String maskedCardNumber,

        String cardHolderName,

        CardType cardType,

        CardStatus cardStatus,

        YearMonth expirationDate,

        LocalDateTime createdAt

) {
}
