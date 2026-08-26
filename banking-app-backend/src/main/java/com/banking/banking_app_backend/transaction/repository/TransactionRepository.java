package com.banking.banking_app_backend.transaction.repository;

import com.banking.banking_app_backend.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccountIdIn(List<Long> accountIds);

    List<Transaction> findAllByAccountId(Long accountId);

}
