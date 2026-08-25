package com.banking.banking_app_backend.account.service;

import com.banking.banking_app_backend.account.dto.response.AccountResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    public List<AccountResponse> getAllAccounts();

    public AccountResponse getAccount(Long id);

}
