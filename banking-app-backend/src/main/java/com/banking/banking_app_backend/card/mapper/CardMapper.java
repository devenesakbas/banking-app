package com.banking.banking_app_backend.card.mapper;

import com.banking.banking_app_backend.card.dto.response.CardResponse;
import com.banking.banking_app_backend.card.dto.response.CreditCardResponse;
import com.banking.banking_app_backend.card.entity.Card;
import com.banking.banking_app_backend.card.entity.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CardMapper {

    @Mapping(target = "availableCredit", expression = "java(creditCard.getCreditLimit().subtract(creditCard.getCurrentDebt()))")
    public abstract CreditCardResponse creditCardToCreditCardResponse(CreditCard creditCard);

    public CardResponse cardToCardResponse(Card card, CreditCard creditCard) {
        if (card == null) {
            return null;
        }

        CreditCardResponse creditCardResponse = creditCard != null
                ? creditCardToCreditCardResponse(creditCard)
                : null;

        return new CardResponse(
                card.getId(),
                maskCardNumber(card.getCardNumber()),
                card.getCardHolderName(),
                card.getCardType(),
                card.getCardStatus(),
                card.getExpirationDate(),
                creditCardResponse
        );
    }

    public String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return cardNumber;
        }
        String lastFour = cardNumber.substring(12);
        return String.format("**** **** **** %s", lastFour);
    }
}