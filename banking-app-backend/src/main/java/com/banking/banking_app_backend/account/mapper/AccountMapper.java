package com.banking.banking_app_backend.account.mapper;

import com.banking.banking_app_backend.account.dto.response.AccountInsertResponse;
import com.banking.banking_app_backend.account.dto.response.AccountResponse;
import com.banking.banking_app_backend.account.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse accountToAccountResponse(Account account);

    AccountInsertResponse accountToAccountInsertResponse(Account account);

}
