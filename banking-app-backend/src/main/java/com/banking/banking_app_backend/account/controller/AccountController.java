package com.banking.banking_app_backend.account.controller;

import com.banking.banking_app_backend.account.dto.response.AccountResponse;
import com.banking.banking_app_backend.account.service.AccountService;
import com.banking.banking_app_backend.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account/")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts(){
        List<AccountResponse> response = accountService.getAllAccounts();

        return ResponseEntity.ok(
                ApiResponse.success(response, "Users fetched successfully")
        );
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(Long id){
        AccountResponse response = accountService.getAccount(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, "User fetched successfully")
        );
    }

}
