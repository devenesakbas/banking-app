package com.banking.banking_app_backend.card.repository;

import com.banking.banking_app_backend.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByAccountIdIn(List<Long> accountIds);

    Boolean existsByCardNumber(String cardNumber);
}
