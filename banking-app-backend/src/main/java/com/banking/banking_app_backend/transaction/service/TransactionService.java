package com.banking.banking_app_backend.transaction.service;

import com.banking.banking_app_backend.transaction.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getAllTransaction();

    TransactionResponse getTransaction(Long id);

    List<TransactionResponse> getTransactionAllByAccountId(Long id);

}
