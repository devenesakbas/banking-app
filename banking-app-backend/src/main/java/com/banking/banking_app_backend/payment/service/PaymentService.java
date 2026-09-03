package com.banking.banking_app_backend.payment.service;

import com.banking.banking_app_backend.payment.dto.request.*;
import com.banking.banking_app_backend.payment.dto.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse handleDeposit(PaymentDepositRequest depositRequest);

    PaymentResponse handleWithdraw(PaymentWithdrawRequest withdrawRequest);

    PaymentResponse handleTransfer(PaymentTransferRequest transferRequest);

    PaymentResponse handleCardCharge(PaymentCardChargeRequest cardChargeRequest);

    PaymentResponse handleCreditCardDebtPayment(PaymentCreditCardDebtPaymentRequest creditCardDebtPaymentRequest);

}
