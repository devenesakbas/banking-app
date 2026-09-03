package com.banking.banking_app_backend.account.controller;

import com.banking.banking_app_backend.account.dto.request.AccountInsertRequest;
import com.banking.banking_app_backend.account.dto.response.AccountInsertResponse;
import com.banking.banking_app_backend.account.dto.response.AccountResponse;
import com.banking.banking_app_backend.account.service.AccountService;
import com.banking.banking_app_backend.common.response.ApiResponse;
import com.banking.banking_app_backend.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts(@AuthenticationPrincipal User user){
        List<AccountResponse> response = accountService.getAllAccounts(user);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Account fetched successfully")
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountInsertResponse>> newAccount(@RequestBody @Valid AccountInsertRequest request, @AuthenticationPrincipal User user){

        AccountInsertResponse response = accountService.newAccount(request, user);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Created account")
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable Long id, @AuthenticationPrincipal User user){
        AccountResponse response = accountService.getAccount(id, user);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Account fetched successfully")
        );
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PatchMapping("/{id}/freeze")
    public ResponseEntity<ApiResponse<AccountResponse>> setAccountFreeze(@PathVariable Long id, @AuthenticationPrincipal User user){
        AccountResponse response = accountService.setAccountFreeze(id, user);
        return ResponseEntity.ok(
                ApiResponse.success(response, "Account updated successfully")
        );
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PatchMapping("/{id}/unfreeze")
    public ResponseEntity<ApiResponse<AccountResponse>> setAccountUnfreeze(@PathVariable Long id, @AuthenticationPrincipal User user){
        AccountResponse response = accountService.setAccountUnfreeze(id, user);
        return ResponseEntity.ok(
                ApiResponse.success(response, "Account updated successfully")
        );
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PatchMapping("/{id}/close")
    public ResponseEntity<ApiResponse<AccountResponse>> setAccountClose(@PathVariable Long id, @AuthenticationPrincipal User user){
        AccountResponse response = accountService.setAccountClosed(id, user);
        return ResponseEntity.ok(
                ApiResponse.success(response, "Account updated successfully")
        );
    }

}
