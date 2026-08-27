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
import com.banking.banking_app_backend.card.exception.CardNotFoundException;
import com.banking.banking_app_backend.card.exception.IllegalCardStatusException;
import com.banking.banking_app_backend.card.exception.UnauthorizedCardAccessException;
import com.banking.banking_app_backend.card.mapper.CardMapper;
import com.banking.banking_app_backend.card.repository.CardRepository;
import com.banking.banking_app_backend.card.service.CardService;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.entity.UserRole;
import com.banking.banking_app_backend.user.exception.UserNotFoundException;
import com.banking.banking_app_backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;

    @Override
    public List<CardResponse> getAllCards(){
        User user = SecurityUtils.getCurrentUser();

        List<Card> cards;

        if(user.getRole() == UserRole.ROLE_SUPER_ADMIN){
            cards = cardRepository.findAll();
        }
        else {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());
            cards = cardRepository.findAllByAccountIdIn(byAccountIds);
        }

        return cards
                .stream()
                .map(cardMapper::cardToCardResponse)
                .toList();
    }

    @Transactional
    @Override
    public CardResponse insertCard(CardInsertRequest request){
        User user = SecurityUtils.getCurrentUser();

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        User accountUser = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            if (!account.getUserId().equals(user.getId())) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new InvalidAccountException("Account is not active");
        }

        Card card = Card.builder()
                .accountId(account.getId())
                .cardNumber(generateCardNumber())
                .cardHolderName(accountUser.getName() + " " + accountUser.getSurname())
                .cardType(request.cardType())
                .cardStatus(CardStatus.ACTIVE)
                .expirationDate(YearMonth.now().plusYears(5))
                .build();

        Card newCard = cardRepository.save(card);

        return cardMapper.cardToCardResponse(newCard);
    }

    @Override
    public CardResponse getCard(Long id){
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(id)
                .orElseThrow(()-> new CardNotFoundException("Card not found"));

        if(user.getRole() != UserRole.ROLE_SUPER_ADMIN){
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());

            if(!byAccountIds.contains(card.getAccountId())){
                throw new UnauthorizedCardAccessException("unauthorized card access");
            }
        }

        return cardMapper.cardToCardResponse(card);
    }

    @Transactional
    @Override
    public CardResponse handleFreeze(Long id){
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            if (!account.getUserId().equals(user.getId())) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new IllegalAccountStateException("Account is not active");
        }

        if(card.getCardStatus() != CardStatus.ACTIVE){
            throw new IllegalCardStatusException("Card is not active");
        }

        card.setCardStatus(CardStatus.FROZEN);

        Card uptadetCard = cardRepository.save(card);

        return cardMapper.cardToCardResponse(uptadetCard);

    }

    @Transactional
    @Override
    public CardResponse handleUnfreeze(Long id){
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            if (!account.getUserId().equals(user.getId())) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new IllegalAccountStateException("Account is not active");
        }

        if(card.getCardStatus() != CardStatus.FROZEN){
            throw new IllegalCardStatusException("Card is not frozen");
        }

        card.setCardStatus(CardStatus.ACTIVE);

        Card updatedCard = cardRepository.save(card);

        return cardMapper.cardToCardResponse(updatedCard);
    }

    @Transactional
    @Override
    public CardResponse handleClose(Long id){
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(()->new AccountNotFoundException("Account not found"));

        if(user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            if (!account.getUserId().equals(user.getId())) {
                throw new UnauthorizedAccountAccessException("Unauthorized account access");
            }
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new IllegalAccountStateException("Account is not active");
        }

        if(card.getCardStatus() == CardStatus.CLOSED){
            throw new IllegalCardStatusException("Already card closed");
        }

        card.setCardStatus(CardStatus.CLOSED);

        Card updatedCard = cardRepository.save(card);

        return cardMapper.cardToCardResponse(updatedCard);
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

            for(int i=0; i<16; i++){
                builder.append(ThreadLocalRandom.current().nextInt(10));
            }

            cardNumber = builder.toString();
        } while(cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

}
