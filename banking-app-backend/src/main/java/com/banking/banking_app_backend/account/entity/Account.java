package com.banking.banking_app_backend.account.entity;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name = "accounts",
        indexes = {
                @Index(name = "idx_account_user_id", columnList = "user_id")
        }
)
public class Account {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull( message = ValidationMessages.NOT_NULL)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank( message = ValidationMessages.NOT_BLANK)
    @Size( max = 20, min = 10)
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @NotBlank(message = ValidationMessages.NOT_BLANK)
    @Size(max = 26, min = 26)
    @Column(name = "iban", nullable = false, unique = true, length = 26)
    private String iban;

    @NotNull( message = ValidationMessages.NOT_NULL )
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @NotNull( message = ValidationMessages.NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "account_currency", nullable = false )
    private AccountCurrency accountCurrency;

    @NotNull( message = ValidationMessages.NOT_NULL)
    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @NotNull( message = ValidationMessages.NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name="account_status", nullable = false)
    private AccountStatus accountStatus;

    @NotNull
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
