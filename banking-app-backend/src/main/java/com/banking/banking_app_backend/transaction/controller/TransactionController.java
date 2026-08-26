package com.banking.banking_app_backend.transaction.controller;

import com.banking.banking_app_backend.common.response.ApiResponse;
import com.banking.banking_app_backend.transaction.dto.response.TransactionResponse;
import com.banking.banking_app_backend.transaction.service.TransactionService;
import com.banking.banking_app_backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllTransaction(){
        List<TransactionResponse> response = transactionService.getAllTransaction();

        return ResponseEntity.ok(
                ApiResponse.success(response, "Fetched transactions successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransaction(@PathVariable Long id){
        TransactionResponse response = transactionService.getTransaction(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Fetched transaction successfully")
        );
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionAllByAccountId(@PathVariable Long id){
        List<TransactionResponse> response = transactionService.getTransactionAllByAccountId(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Fetched transaction successfully")
        );
    }

}
