package com.banking.banking_app_backend.payment.controller;

import com.banking.banking_app_backend.common.response.ApiResponse;
import com.banking.banking_app_backend.payment.dto.request.*;
import com.banking.banking_app_backend.payment.dto.response.PaymentResponse;
import com.banking.banking_app_backend.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<PaymentResponse>> handleDeposit(@RequestBody @Valid PaymentDepositRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(paymentService.handleDeposit(request), "Transaction successfully")
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<PaymentResponse>> handleWithdraw(@RequestBody @Valid PaymentWithdrawRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(paymentService.handleWithdraw(request), "Transaction successfully")
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<PaymentResponse>> handleTransfer(@RequestBody @Valid PaymentTransferRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(paymentService.handleTransfer(request), "Transaction successfully")
        );
    }

    @PostMapping("/card-charge")
    public ResponseEntity<ApiResponse<PaymentResponse>> handleCardCharger(@RequestBody @Valid PaymentCardChargeRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(paymentService.handleCardCharge(request), "Card charge successfully")
        );
    }

    @PostMapping("/credit-card/debt-payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> handleCreditCardDebtPayment(@RequestBody @Valid PaymentCreditCardDebtPaymentRequest request){
        return ResponseEntity.ok(
          ApiResponse.success(paymentService.handleCreditCardDebtPayment(request), "Credit card debt payment successfully")
        );
    }

}
