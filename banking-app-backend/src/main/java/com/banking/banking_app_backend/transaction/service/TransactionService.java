package com.banking.banking_app_backend.transaction.service;

import com.banking.banking_app_backend.transaction.dto.request.TransactionDepositRequest;
import com.banking.banking_app_backend.transaction.dto.request.TransactionTransferRequest;
import com.banking.banking_app_backend.transaction.dto.request.TransactionWithdrawRequest;
import com.banking.banking_app_backend.transaction.dto.response.TransactionResponse;
import com.banking.banking_app_backend.user.entity.User;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getAllTransaction();

    TransactionResponse getTransaction(Long id);

    List<TransactionResponse> getTransactionAllByAccountId(Long id);

    TransactionResponse handleDeposit(TransactionDepositRequest request);

    TransactionResponse handleWithdraw(TransactionWithdrawRequest request);

    TransactionResponse handleTransfer(TransactionTransferRequest request);

}
