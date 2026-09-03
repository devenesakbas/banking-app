package com.banking.banking_app_backend.card.repository;

import com.banking.banking_app_backend.card.entity.CreditCard;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    List<CreditCard> findAllByCardIdIn(List<Long> cardIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cc FROM CreditCard cc WHERE cc.cardId = :cardId")
    Optional<CreditCard> findByCardIdForUpdate(@Param("cardId") Long cardId);

}
