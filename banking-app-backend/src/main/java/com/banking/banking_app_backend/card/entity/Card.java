package com.banking.banking_app_backend.card.entity;

import com.banking.banking_app_backend.common.exception.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "cards",
        indexes = {
                @Index(
                        name = "idx_cards_account_id",
                        columnList = "account_id"
                )
        }
)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @NotBlank(message = ValidationMessages.NOT_BLANK)
    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @NotBlank(message = ValidationMessages.NOT_BLANK)
    @Size(min = 5, max = 100, message = ValidationMessages.CARD_HOLDER_NAME_SIZE)
    @Column(name = "card_holder_name", nullable = false, length = 100)
    private String cardHolderName;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus;

    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "expiration_date", nullable = false)
    private YearMonth expirationDate;

    @CreationTimestamp
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
