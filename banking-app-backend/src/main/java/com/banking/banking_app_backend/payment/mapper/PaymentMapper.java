package com.banking.banking_app_backend.payment.mapper;

import com.banking.banking_app_backend.payment.dto.response.PaymentResponse;
import com.banking.banking_app_backend.transaction.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponse paymentResponseToTransaction(Transaction transaction);

}
