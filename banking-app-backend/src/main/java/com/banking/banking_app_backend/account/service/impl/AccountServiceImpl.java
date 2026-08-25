package com.banking.banking_app_backend.account.service.impl;

import com.banking.banking_app_backend.account.dto.response.AccountResponse;
import com.banking.banking_app_backend.account.entity.Account;
import com.banking.banking_app_backend.account.exception.AccountNotFoundException;
import com.banking.banking_app_backend.account.mapper.AccountMapper;
import com.banking.banking_app_backend.account.repository.AccountRepository;
import com.banking.banking_app_backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    @Override
    public List<AccountResponse> getAllAccounts(){
        return accountRepository.findAll().stream().map(accountMapper::accountToAccountResponse).toList();
    }

    @Override
    public AccountResponse getAccount(Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(("Account not found")));

        return accountMapper.accountToAccountResponse(account);
    }
}
