package com.banking.banking_app_backend.card.service;

import com.banking.banking_app_backend.card.dto.request.CardInsertRequest;
import com.banking.banking_app_backend.card.dto.response.CardResponse;

import java.util.List;

public interface CardService {

    List<CardResponse> getAllCards();

    CardResponse insertCard(CardInsertRequest request);

    CardResponse getCard(Long id);

    CardResponse handleFreeze(Long id);

    CardResponse handleUnfreeze(Long id);

    CardResponse handleClose(Long id);

}
