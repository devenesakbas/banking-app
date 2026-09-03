package com.banking.banking_app_backend.transaction.service.impl;

import com.banking.banking_app_backend.account.entity.Account;
import com.banking.banking_app_backend.account.repository.AccountRepository;
import com.banking.banking_app_backend.auth.security.SecurityUtils;
import com.banking.banking_app_backend.transaction.dto.response.TransactionResponse;
import com.banking.banking_app_backend.transaction.entity.Transaction;
import com.banking.banking_app_backend.transaction.exception.TransactionNotFoundException;
import com.banking.banking_app_backend.transaction.exception.UnauthorizedTransactionAccessException;
import com.banking.banking_app_backend.transaction.mapper.TransactionMapper;
import com.banking.banking_app_backend.transaction.repository.TransactionRepository;
import com.banking.banking_app_backend.transaction.service.TransactionService;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    @Override
    public List<TransactionResponse> getAllTransaction() {
        User user = SecurityUtils.getCurrentUser();
        List<Transaction> transactions;

        if (user.getRole() == UserRole.ROLE_SUPER_ADMIN) {
            transactions = transactionRepository.findAll();
        } else {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());
            transactions = transactionRepository.findAllByAccountIdIn(byAccountIds);
        }

        return transactions
                .stream()
                .map(transactionMapper::transactionToTransactionResponse)
                .toList();
    }

    @Override
    public TransactionResponse getTransaction(Long id) {
        User user = SecurityUtils.getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());

            if (user.getRole() == UserRole.ROLE_USER && !byAccountIds.contains(transaction.getAccountId())) {
                throw new UnauthorizedTransactionAccessException("Unauthorized transaction access");
            }
        }

        return transactionMapper.transactionToTransactionResponse(transaction);

    }

    @Override
    public List<TransactionResponse> getTransactionAllByAccountId(Long id) {
        User user = SecurityUtils.getCurrentUser();

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());

            if (!byAccountIds.contains(id)) {
                throw new UnauthorizedTransactionAccessException("Unauthorized transaction access");
            }
        }

        List<Transaction> transactions = transactionRepository.findAllByAccountId(id);

        return transactions
                .stream()
                .map(transactionMapper::transactionToTransactionResponse)
                .toList();

    }

    private List<Long> getAccountIdsByUserId(Long userId) {
        return accountRepository.findAllByUserId(userId)
                .stream()
                .map(Account::getId)
                .toList();
    }

}