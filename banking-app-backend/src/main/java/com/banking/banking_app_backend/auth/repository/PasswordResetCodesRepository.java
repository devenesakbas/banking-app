package com.banking.banking_app_backend.auth.repository;

import com.banking.banking_app_backend.auth.entity.PasswordResetCodes;
import com.banking.banking_app_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetCodesRepository extends JpaRepository<PasswordResetCodes, Long> {

    Optional<PasswordResetCodes> findByUserAndCodeAndIsUsedFalseAndExpiresAtAfter(User user, String code, LocalDateTime expiresAt);

    Optional<PasswordResetCodes> findTopByUserAndIsUsedFalseAndExpiresAtAfterOrderByExpiresAtDesc(
            User user, LocalDateTime now);

    List<PasswordResetCodes> findByUserAndIsUsedFalse(User user);

    @Query("SELECT CASE WHEN COUNT(prc) > 0 THEN true ELSE false END FROM PasswordResetCodes prc WHERE prc.user = :user AND prc.code = :code AND prc.isUsed = false AND prc.expiresAt > :expiresAt")
    boolean activeResetCode(User user, String code, LocalDateTime expiresAt);

}
