package com.banking.banking_app_backend.transaction.entity;

import com.banking.banking_app_backend.account.entity.AccountCurrency;
import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name="transactions",
        indexes = {
                @Index(
                        name = "idx_transaction_account_id",
                        columnList = "account_id"
                ),
                @Index(
                        name = "idx_transaction_reference_number",
                        columnList = "reference_number"
                )
        }
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "account_id", nullable = false)
    Long accountId;

    @Column(name = "card_id", nullable = true)
    Long cardId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "transaction_type", nullable = false)
    TransactionType transactionType;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "currency", nullable = false)
    AccountCurrency currency;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "balance_before", nullable = false, precision = 19, scale = 4)
    BigDecimal balanceBefore;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "balance_after", nullable = false, precision = 19, scale = 4)
    BigDecimal balanceAfter;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "reference_number", nullable = false, unique = true, length = 36)
    String referenceNumber;

    @Column(name = "transfer_reference", nullable = true, length = 36)
    String transferReference;

    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "transaction_status", nullable = false)
    TransactionStatus transactionStatus;

    @CreationTimestamp
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;
}
