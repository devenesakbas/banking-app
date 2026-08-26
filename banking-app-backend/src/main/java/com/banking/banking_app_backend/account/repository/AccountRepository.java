package com.banking.banking_app_backend.account.repository;

import com.banking.banking_app_backend.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByUserId(Long id);

    Boolean existsByAccountNumber(String accountNumber);

    Boolean existsByIban(String iban);

}
