package com.banking.banking_app_backend.card.entity;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "credit_cards",
        indexes = {
                @Index(
                        name = "idx_credit_cards_card_id",
                        columnList = "card_id"
                )
        }
)
public class CreditCard {

    @Id
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "card_id", nullable = false, unique = true)
    private Long cardId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @DecimalMin(value = "0.00")
    @Column(
            name =  "credit_limit",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal creditLimit;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @DecimalMin(value = "0.00")
    @Column(
            name = "current_debt",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal currentDebt;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @DecimalMin(value = "0.00")
    @Column(
            name = "minimum_payment",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal minimumPayment;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @CreationTimestamp
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
