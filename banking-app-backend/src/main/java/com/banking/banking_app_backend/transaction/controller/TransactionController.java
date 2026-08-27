package com.banking.banking_app_backend.transaction.controller;

import com.banking.banking_app_backend.common.response.ApiResponse;
import com.banking.banking_app_backend.transaction.dto.request.TransactionDepositRequest;
import com.banking.banking_app_backend.transaction.dto.request.TransactionTransferRequest;
import com.banking.banking_app_backend.transaction.dto.request.TransactionWithdrawRequest;
import com.banking.banking_app_backend.transaction.dto.response.TransactionResponse;
import com.banking.banking_app_backend.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<TransactionResponse>> handleDeposit(@RequestBody @Valid TransactionDepositRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(transactionService.handleDeposit(request), "Transaction successfully")
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<TransactionResponse>> handleWithdraw(@RequestBody @Valid TransactionWithdrawRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(transactionService.handleWithdraw(request), "Transaction successfully")
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponse>> handleTransfer(@RequestBody @Valid TransactionTransferRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(transactionService.handleTransfer(request), "Transaction successfully")
        );
    }

}
