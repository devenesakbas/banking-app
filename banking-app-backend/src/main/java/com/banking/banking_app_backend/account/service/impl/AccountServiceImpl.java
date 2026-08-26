package com.banking.banking_app_backend.account.service.impl;

import com.banking.banking_app_backend.account.dto.request.AccountInsertRequest;
import com.banking.banking_app_backend.account.dto.response.AccountInsertResponse;
import com.banking.banking_app_backend.account.dto.response.AccountResponse;
import com.banking.banking_app_backend.account.entity.Account;
import com.banking.banking_app_backend.account.entity.AccountStatus;
import com.banking.banking_app_backend.account.exception.AccountNotFoundException;
import com.banking.banking_app_backend.account.exception.IllegalAccountStateException;
import com.banking.banking_app_backend.account.exception.UnauthorizedAccountAccessException;
import com.banking.banking_app_backend.account.mapper.AccountMapper;
import com.banking.banking_app_backend.account.repository.AccountRepository;
import com.banking.banking_app_backend.account.service.AccountService;
import com.banking.banking_app_backend.account.util.AccountNumberGenerator;
import com.banking.banking_app_backend.account.util.IbanGenerator;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.entity.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountNumberGenerator accountNumberGenerator;

    @Override
    public List<AccountResponse> getAllAccounts(User user) {

        List<Account> accounts;

        if (user.getRole() == UserRole.ROLE_SUPER_ADMIN) {
            accounts = accountRepository.findAll();
        } else {
            accounts = accountRepository.findAllByUserId(user.getId());
        }

        return accounts.stream()
                .map(accountMapper::accountToAccountResponse)
                .toList();

    }

    @Transactional
    @Override
    public AccountInsertResponse newAccount(AccountInsertRequest request, User user) {

        String accountNumber = accountNumberGenerator.generate();
        String iban = IbanGenerator.generate(accountNumber);
        BigDecimal balance = BigDecimal.ZERO;

        Account account = new Account();
        account.setUserId(user.getId());
        account.setAccountNumber(accountNumber);
        account.setIban(iban);
        account.setAccountType(request.accountType());
        account.setAccountCurrency(request.accountCurrency());
        account.setBalance(balance);
        account.setAccountStatus(AccountStatus.ACTIVE);

        Account newAccount = accountRepository.save(account);

        return accountMapper.accountToAccountInsertResponse(newAccount);
    }

    @Override
    public AccountResponse getAccount(Long id, User user) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found")
                );

        if (user.getRole() == UserRole.ROLE_USER && !account.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccountAccessException("Unauthorized access to account numbers");
        }

        return accountMapper.accountToAccountResponse(account);
    }

    @Transactional
    @Override
    public AccountResponse setAccountFreeze(Long id, User user){

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(user.getRole() != UserRole.ROLE_SUPER_ADMIN){
            throw new UnauthorizedAccountAccessException("Unauthorized access to account numbers");
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new IllegalAccountStateException("Invalid account status");
        }

        account.setAccountStatus(AccountStatus.FROZEN);

        Account updatedAccount = accountRepository.save(account);

        return accountMapper.accountToAccountResponse(updatedAccount);

    }

    @Transactional
    @Override
    public AccountResponse setAccountUnfreeze(Long id, User user){

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(user.getRole() != UserRole.ROLE_SUPER_ADMIN){
            throw new UnauthorizedAccountAccessException("Unauthorized access to account numbers");
        }

        if(account.getAccountStatus() != AccountStatus.FROZEN){
            throw new IllegalAccountStateException("Invalid account status");
        }

        account.setAccountStatus(AccountStatus.ACTIVE);

        Account updatedAccount = accountRepository.save(account);

        return accountMapper.accountToAccountResponse(updatedAccount);

    }

    @Transactional
    @Override
    public AccountResponse setAccountClosed(Long id, User user){

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(user.getRole() != UserRole.ROLE_SUPER_ADMIN){
            throw new UnauthorizedAccountAccessException("Unauthorized access to account numbers");
        }

        if(account.getAccountStatus() == AccountStatus.CLOSED){
            throw new IllegalAccountStateException("Invalid account status");
        }

        account.setAccountStatus(AccountStatus.CLOSED);

        Account updatedAccount = accountRepository.save(account);

        return accountMapper.accountToAccountResponse(updatedAccount);

    }

}
