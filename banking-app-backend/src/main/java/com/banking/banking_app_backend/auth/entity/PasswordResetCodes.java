package com.banking.banking_app_backend.auth.entity;

import com.banking.banking_app_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="password_reset_codes")
public class PasswordResetCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name="id", nullable=false)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable=false)
    User user;

    @Column( name = "code", nullable=false)
    String code;

    @Column( name = "expires_at", nullable=false)
    LocalDateTime expiresAt;

    @Column( name = "is_used", nullable=false)
    Boolean isUsed = false;

}
