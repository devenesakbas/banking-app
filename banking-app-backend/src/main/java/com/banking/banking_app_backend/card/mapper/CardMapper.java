package com.banking.banking_app_backend.card.mapper;

import com.banking.banking_app_backend.card.dto.response.CardResponse;
import com.banking.banking_app_backend.card.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "cardNumber", expression = "java(maskCardNumber(card.getCardNumber()))")
    CardResponse cardToCardResponse(Card card);

    default String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return cardNumber;
        }
        String lastFour = cardNumber.substring(12);
        return String.format("**** **** **** %s", lastFour);
    }

}
