package com.banking.banking_app_backend.card.dto.response;

import com.banking.banking_app_backend.card.entity.CardStatus;
import com.banking.banking_app_backend.card.entity.CardType;

import java.time.YearMonth;

public record CardResponse(

        Long id,

        String maskedCardNumber,

        String cardHolderName,

        CardType cardType,

        CardStatus cardStatus,

        YearMonth expirationDate,

        CreditCardResponse creditCard

) {
}
