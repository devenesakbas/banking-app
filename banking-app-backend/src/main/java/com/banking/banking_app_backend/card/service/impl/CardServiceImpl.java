package com.banking.banking_app_backend.card.service.impl;

import com.banking.banking_app_backend.account.entity.Account;
import com.banking.banking_app_backend.account.entity.AccountStatus;
import com.banking.banking_app_backend.account.exception.AccountNotFoundException;
import com.banking.banking_app_backend.account.exception.IllegalAccountStateException;
import com.banking.banking_app_backend.account.exception.InvalidAccountException;
import com.banking.banking_app_backend.account.exception.UnauthorizedAccountAccessException;
import com.banking.banking_app_backend.account.repository.AccountRepository;
import com.banking.banking_app_backend.auth.security.SecurityUtils;
import com.banking.banking_app_backend.card.dto.request.CardInsertRequest;
import com.banking.banking_app_backend.card.dto.response.CardResponse;
import com.banking.banking_app_backend.card.entity.Card;
import com.banking.banking_app_backend.card.entity.CardStatus;
import com.banking.banking_app_backend.card.entity.CardType;
import com.banking.banking_app_backend.card.entity.CreditCard;
import com.banking.banking_app_backend.card.exception.CardNotFoundException;
import com.banking.banking_app_backend.card.exception.IllegalCardStatusException;
import com.banking.banking_app_backend.card.exception.UnauthorizedCardAccessException;
import com.banking.banking_app_backend.card.mapper.CardMapper;
import com.banking.banking_app_backend.card.repository.CardRepository;
import com.banking.banking_app_backend.card.repository.CreditCardRepository;
import com.banking.banking_app_backend.card.service.CardService;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.entity.UserRole;
import com.banking.banking_app_backend.user.exception.UserNotFoundException;
import com.banking.banking_app_backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;

    @Value("${card.credit-card.credit-limit}")
    private BigDecimal creditLimit;

    @Value("${card.credit-card.current-debt}")
    private BigDecimal currentDebt;

    @Value("${card.credit-card.minimum-payment}")
    private BigDecimal minimumPayment;

    @Value("${card.credit-card.expiration-years}")
    private int expirationYears;

    @Value("${card.credit-card.payment-due-in-days}")
    private int paymentDueInDays;

    @Override
    public List<CardResponse> getAllCards() {
        User user = SecurityUtils.getCurrentUser();

        List<Card> cards;

        if (user.getRole() == UserRole.ROLE_SUPER_ADMIN) {
            cards = cardRepository.findAll();
        } else {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());
            cards = cardRepository.findAllByAccountIdIn(byAccountIds);
        }

        return mapCardsWithCreditDetails(cards);
    }

    @Transactional
    @Override
    public CardResponse insertCard(CardInsertRequest request) {
        User user = SecurityUtils.getCurrentUser();

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        User accountUser = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            if (!account.getUserId().equals(user.getId())) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException("Account is not active");
        }

        Card card = Card.builder()
                .accountId(account.getId())
                .cardNumber(generateCardNumber())
                .cardHolderName(accountUser.getName() + " " + accountUser.getSurname())
                .cardType(request.cardType())
                .cardStatus(CardStatus.ACTIVE)
                .expirationDate(YearMonth.now().plusYears(expirationYears))
                .build();

        Card newCard = cardRepository.save(card);

        CreditCard savedCreditCard = null;

        if (newCard.getCardType() == CardType.CREDIT) {
            CreditCard creditCard = CreditCard.builder()
                    .cardId(newCard.getId())
                    .creditLimit(creditLimit)
                    .currentDebt(currentDebt)
                    .minimumPayment(minimumPayment)
                    .dueDate(LocalDate.now().plusYears(paymentDueInDays))
                    .build();

            savedCreditCard = creditCardRepository.save(creditCard);
        }

        return cardMapper.cardToCardResponse(newCard, savedCreditCard);
    }

    @Override
    public CardResponse getCard(Long id) {
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());

            if (!byAccountIds.contains(card.getAccountId())) {
                throw new UnauthorizedCardAccessException("unauthorized card access");
            }
        }

        CreditCard creditCard = card.getCardType() == CardType.CREDIT
                ? creditCardRepository.findById(card.getId()).orElse(null)
                : null;

        return cardMapper.cardToCardResponse(card, creditCard);
    }

    @Transactional
    @Override
    public CardResponse handleFreeze(Long id) {
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            if (!account.getUserId().equals(user.getId())) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalAccountStateException("Account is not active");
        }

        if (card.getCardStatus() != CardStatus.ACTIVE) {
            throw new IllegalCardStatusException("Card is not active");
        }

        card.setCardStatus(CardStatus.FROZEN);

        Card updatedCard = cardRepository.save(card);

        CreditCard creditCard = updatedCard.getCardType() == CardType.CREDIT
                ? creditCardRepository.findById(updatedCard.getId()).orElse(null)
                : null;

        return cardMapper.cardToCardResponse(updatedCard, creditCard);

    }

    @Transactional
    @Override
    public CardResponse handleUnfreeze(Long id) {
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            if (!account.getUserId().equals(user.getId())) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalAccountStateException("Account is not active");
        }

        if (card.getCardStatus() != CardStatus.FROZEN) {
            throw new IllegalCardStatusException("Card is not frozen");
        }

        card.setCardStatus(CardStatus.ACTIVE);

        Card updatedCard = cardRepository.save(card);

        CreditCard creditCard = updatedCard.getCardType() == CardType.CREDIT
                ? creditCardRepository.findById(updatedCard.getId()).orElse(null)
                : null;

        return cardMapper.cardToCardResponse(updatedCard, creditCard);
    }

    @Transactional
    @Override
    public CardResponse handleClose(Long id) {
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            if (!account.getUserId().equals(user.getId())) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalAccountStateException("Account is not active");
        }

        if (card.getCardStatus() == CardStatus.CLOSED) {
            throw new IllegalCardStatusException("Already card closed");
        }

        card.setCardStatus(CardStatus.CLOSED);

        Card updatedCard = cardRepository.save(card);

        CreditCard creditCard = updatedCard.getCardType() == CardType.CREDIT
                ? creditCardRepository.findById(updatedCard.getId()).orElse(null)
                : null;

        return cardMapper.cardToCardResponse(updatedCard, creditCard);
    }

    @Override
    public List<CardResponse> getCardsByAccountId(Long id) {
        User user = SecurityUtils.getCurrentUser();

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());

            if (!byAccountIds.contains(id)) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        List<Card> cards = cardRepository.findAllByAccountIdIn(List.of(id));

        return mapCardsWithCreditDetails(cards);
    }

    @Override
    public void updateCardsByAccountStatus(Long accountId, AccountStatus status) {

        List<Card> cards = cardRepository.findAllByAccountIdIn(List.of(accountId));

        for (Card card : cards) {

            switch (status) {

                case ACTIVE:
                    if (card.getCardStatus() == CardStatus.FROZEN) {
                        card.setCardStatus(CardStatus.ACTIVE);
                    }
                    break;

                case FROZEN:
                    if (card.getCardStatus() == CardStatus.ACTIVE) {
                        card.setCardStatus(CardStatus.FROZEN);
                    }
                    break;

                case CLOSED:
                    if (card.getCardStatus() != CardStatus.CLOSED) {
                        card.setCardStatus(CardStatus.CLOSED);
                    }
                    break;
            }
        }

        cardRepository.saveAll(cards);

    }

    private List<Long> getAccountIdsByUserId(Long userId) {
        return accountRepository.findAllByUserId(userId)
                .stream()
                .map(Account::getId)
                .toList();
    }

    private String generateCardNumber() {
        String cardNumber;
        do {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < 16; i++) {
                builder.append(ThreadLocalRandom.current().nextInt(10));
            }

            cardNumber = builder.toString();
        } while (cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    private List<CardResponse> mapCardsWithCreditDetails(List<Card> cards) {
        List<Long> creditCardIds = cards.stream()
                .filter(c -> c.getCardType() == CardType.CREDIT)
                .map(Card::getId)
                .toList();

        Map<Long, CreditCard> creditCardsByCardId = creditCardRepository
                .findAllByCardIdIn(creditCardIds)
                .stream()
                .collect(Collectors.toMap(CreditCard::getCardId, Function.identity()));

        return cards.stream()
                .map(card -> cardMapper.cardToCardResponse(
                        card,
                        creditCardsByCardId.get(card.getId())
                ))
                .toList();
    }

}
