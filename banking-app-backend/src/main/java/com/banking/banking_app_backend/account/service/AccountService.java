package com.banking.banking_app_backend.account.service;

import com.banking.banking_app_backend.account.dto.request.AccountInsertRequest;
import com.banking.banking_app_backend.account.dto.response.AccountInsertResponse;
import com.banking.banking_app_backend.account.dto.response.AccountResponse;
import com.banking.banking_app_backend.user.entity.User;

import java.util.List;

public interface AccountService {

    List<AccountResponse> getAllAccounts(User user);

    AccountInsertResponse newAccount(AccountInsertRequest request, User user);

    AccountResponse getAccount(Long id, User user);

    AccountResponse setAccountFreeze(Long id, User user);

    AccountResponse setAccountUnfreeze(Long id, User user);

    AccountResponse setAccountClosed(Long id, User user);

}
