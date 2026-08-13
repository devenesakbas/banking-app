package com.banking.banking_app_backend.auth.repository;

import com.banking.banking_app_backend.auth.entity.PasswordResetCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetCodesRepository extends JpaRepository<PasswordResetCodes, Long> {


}
