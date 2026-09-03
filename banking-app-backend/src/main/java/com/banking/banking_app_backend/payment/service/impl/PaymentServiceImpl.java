package com.banking.banking_app_backend.payment.service.impl;

import com.banking.banking_app_backend.account.entity.Account;
import com.banking.banking_app_backend.account.entity.AccountStatus;
import com.banking.banking_app_backend.account.exception.*;
import com.banking.banking_app_backend.account.exception.IllegalArgumentException;
import com.banking.banking_app_backend.account.repository.AccountRepository;
import com.banking.banking_app_backend.auth.security.SecurityUtils;
import com.banking.banking_app_backend.card.entity.Card;
import com.banking.banking_app_backend.card.entity.CardStatus;
import com.banking.banking_app_backend.card.entity.CardType;
import com.banking.banking_app_backend.card.entity.CreditCard;
import com.banking.banking_app_backend.card.exception.*;
import com.banking.banking_app_backend.card.repository.CardRepository;
import com.banking.banking_app_backend.card.repository.CreditCardRepository;
import com.banking.banking_app_backend.payment.dto.request.*;
import com.banking.banking_app_backend.payment.dto.response.PaymentResponse;
import com.banking.banking_app_backend.payment.exception.CreditCardMinimumPaymentException;
import com.banking.banking_app_backend.payment.exception.SameAccountTransferException;
import com.banking.banking_app_backend.payment.mapper.PaymentMapper;
import com.banking.banking_app_backend.payment.service.PaymentService;
import com.banking.banking_app_backend.transaction.entity.Transaction;
import com.banking.banking_app_backend.transaction.entity.TransactionStatus;
import com.banking.banking_app_backend.transaction.entity.TransactionType;
import com.banking.banking_app_backend.transaction.exception.BalanceErrorException;
import com.banking.banking_app_backend.transaction.exception.UnauthorizedTransactionAccessException;
import com.banking.banking_app_backend.transaction.repository.TransactionRepository;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.entity.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CreditCardRepository creditCardRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    @Override
    public PaymentResponse handleDeposit(PaymentDepositRequest request) {
        User user = SecurityUtils.getCurrentUser();
        Account account = accountRepository.findByIdForUpdate(request.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (!account.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccountAccessException("Unauthorized account access");
        }

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException("Account is not active");
        }

        if (account.getAccountCurrency() != request.currency()) {
            throw new InvalidAccountException("Account currency does not match transaction currency");
        }

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }

        BigDecimal balanceBefore = account.getBalance();

        BigDecimal balanceAfter = balanceBefore.add(request.amount());

        account.setBalance(balanceAfter);

        Transaction transaction = Transaction.builder()
                .accountId(account.getId())
                .transactionType(TransactionType.DEPOSIT)
                .amount(request.amount())
                .currency(request.currency())
                .balanceBefore(balanceBefore)
                .balanceAfter(balanceAfter)
                .referenceNumber(generateReferenceNumber())
                .description(request.description())
                .transactionStatus(TransactionStatus.COMPLETED)
                .build();

        Transaction newTransaction = transactionRepository.save(transaction);
        accountRepository.save(account);

        return paymentMapper.paymentResponseToTransaction(newTransaction);
    }

    @Transactional
    @Override
    public PaymentResponse handleWithdraw(PaymentWithdrawRequest request) {
        User user = SecurityUtils.getCurrentUser();

        Account account = accountRepository.findByIdForUpdate(request.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (!account.getUserId().equals(user.getId())) {
            throw new UnauthorizedTransactionAccessException("Unauthorization transaction access");
        }

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException("Account is not active");
        }

        if (!account.getAccountCurrency().equals(request.currency())) {
            throw new InvalidAccountException("Account currency does not match transaction currency");
        }

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }

        if (account.getBalance().compareTo(request.amount()) < 0) {
            throw new BalanceErrorException("Insufficient funds");
        }

        BigDecimal balanceBefore = account.getBalance();

        BigDecimal balanceAfter = balanceBefore.subtract(request.amount());

        account.setBalance(balanceAfter);
        accountRepository.save(account);

        Transaction newTransaction = Transaction.builder()
                .accountId(account.getId())
                .transactionType(TransactionType.WITHDRAW)
                .amount(request.amount())
                .currency(request.currency())
                .balanceBefore(balanceBefore)
                .balanceAfter(balanceAfter)
                .referenceNumber(generateReferenceNumber())
                .description(request.description())
                .transactionStatus(TransactionStatus.COMPLETED)
                .build();

        transactionRepository.save(newTransaction);

        return paymentMapper.paymentResponseToTransaction(newTransaction);
    }

    @Transactional
    @Override
    public PaymentResponse handleTransfer(PaymentTransferRequest request) {
        User user = SecurityUtils.getCurrentUser();

        Account sourceAccount;
        Account destinationAccount;

        if (request.sourceAccountId() == request.destinationAccountId()) {
            throw new SameAccountTransferException("Same accounts transfer");
        }

        if (request.sourceAccountId() < request.destinationAccountId()) {
            sourceAccount = accountRepository.findByIdForUpdate(request.sourceAccountId())
                    .orElseThrow(() -> new AccountNotFoundException("Source account not found"));

            destinationAccount = accountRepository.findByIdForUpdate(request.destinationAccountId())
                    .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

        } else {
            destinationAccount = accountRepository.findByIdForUpdate(request.destinationAccountId())
                    .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

            sourceAccount = accountRepository.findByIdForUpdate(request.sourceAccountId())
                    .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
        }

        if (!sourceAccount.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccountAccessException("Unauthorized account access");
        }

        if (sourceAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException("Source account is not active");
        }

        if (destinationAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException("Destination account is not active");
        }

        if (sourceAccount.getId().equals(destinationAccount.getId())) {
            throw new InvalidAccountException("Invalid source and destination account");
        }

        if (sourceAccount.getAccountCurrency() != request.currency()) {
            throw new InvalidAccountException("Account currency does not match transaction currency");
        }

        if (destinationAccount.getAccountCurrency() != request.currency()) {
            throw new InvalidAccountException(
                    "Destination account currency does not match transaction currency"
            );
        }

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }

        if (sourceAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new BalanceErrorException("Insufficient funds");
        }

        BigDecimal sourceBalanceBefore = sourceAccount.getBalance();

        BigDecimal destinationBalanceBefore = destinationAccount.getBalance();

        BigDecimal sourceBalanceAfter = sourceBalanceBefore.subtract(request.amount());

        BigDecimal destinationBalanceAfter = destinationBalanceBefore.add(request.amount());

        sourceAccount.setBalance(sourceBalanceAfter);
        destinationAccount.setBalance(destinationBalanceAfter);

        String transferReference = generateTransferReference();

        Transaction sourceTransaction = Transaction.builder()
                .accountId(sourceAccount.getId())
                .transactionType(TransactionType.TRANSFER_OUT)
                .amount(request.amount())
                .currency(request.currency())
                .balanceBefore(sourceBalanceBefore)
                .balanceAfter(sourceBalanceAfter)
                .referenceNumber(generateReferenceNumber())
                .transferReference(transferReference)
                .description(request.description())
                .transactionStatus(TransactionStatus.COMPLETED)
                .build();

        Transaction destinationTransaction = Transaction.builder()
                .accountId(destinationAccount.getId())
                .transactionType(TransactionType.TRANSFER_IN)
                .amount(request.amount())
                .currency(request.currency())
                .balanceBefore(destinationBalanceBefore)
                .balanceAfter(destinationBalanceAfter)
                .referenceNumber(generateReferenceNumber())
                .transferReference(transferReference)
                .description(request.description())
                .transactionStatus(TransactionStatus.COMPLETED)
                .build();

        Transaction transaction = transactionRepository.save(sourceTransaction);
        transactionRepository.save(destinationTransaction);

        return paymentMapper.paymentResponseToTransaction(transaction);
    }

    @Override
    public PaymentResponse handleCardCharge(PaymentCardChargeRequest request) {
        User user = SecurityUtils.getCurrentUser();

        Card card = cardRepository.findById(request.cardId())
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN && !account.getUserId().equals(user.getId())) {
            throw new InvalidCardException("Illegal card access");
        }

        if (card.getCardType() != CardType.CREDIT) {
            throw new NotCreditCardException("Not credit card");
        }

        if (card.getCardStatus() != CardStatus.ACTIVE) {
            throw new IllegalCardStatusException("Illegal card status");
        }

        CreditCard creditCard = creditCardRepository.findByCardIdForUpdate(card.getId())
                .orElseThrow(() -> new CreditCardNotFoundException("Credit card not found"));

        BigDecimal debtBefore = creditCard.getCurrentDebt();
        BigDecimal newDebt = debtBefore.add(request.amount());

        if (newDebt.compareTo(creditCard.getCreditLimit()) > 0) {
            throw new InsufficientCreditLimitException("Insufficient credit limit");
        }

        creditCard.setCurrentDebt(newDebt);
        creditCardRepository.save(creditCard);

        Transaction transaction = Transaction.builder()
                .accountId(card.getAccountId())
                .cardId(card.getId())
                .transactionType(TransactionType.CARD_PAYMENT)
                .amount(request.amount())
                .currency(account.getAccountCurrency())
                .balanceBefore(debtBefore)
                .balanceAfter(newDebt)
                .referenceNumber(generateReferenceNumber())
                .description(request.description())
                .transactionStatus(TransactionStatus.COMPLETED)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return paymentMapper.paymentResponseToTransaction(savedTransaction);

    }

    @Override
    public PaymentResponse handleCreditCardDebtPayment(PaymentCreditCardDebtPaymentRequest request) {
        User user = SecurityUtils.getCurrentUser();

        Account account = accountRepository.findByIdForUpdate(request.sourceAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        Card card = cardRepository.findById(request.cardId())
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        CreditCard creditCard = creditCardRepository.findByCardIdForUpdate(request.cardId())
                .orElseThrow(() -> new CreditCardNotFoundException("Credit card not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN && !account.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccountAccessException("Unauthorized account access");
        }

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalAccountStateException("Account is not active");
        }

        if (!card.getAccountId().equals(account.getId())) {
            throw new InvalidCardException("Card does not belong to this account");
        }

        if (card.getCardStatus() != CardStatus.ACTIVE) {
            throw new IllegalCardStatusException("Card is not active");
        }

        if (request.amount().compareTo(creditCard.getCurrentDebt()) > 0) {
            throw new InsufficientCreditLimitException("Payment amount exceeds current debt");
        }

        boolean isFullPayoff = request.amount().compareTo(creditCard.getCurrentDebt()) == 0;

        if (!isFullPayoff && request.amount().compareTo(creditCard.getMinimumPayment()) < 0) {
            throw new CreditCardMinimumPaymentException("Payment is below minimum payment amount");
        }

        if (request.amount().compareTo(account.getBalance()) > 0) {
            throw new BalanceErrorException("Insufficient funds");
        }

        BigDecimal accountBalanceBefore = account.getBalance();
        BigDecimal accountBalanceAfter = accountBalanceBefore.subtract(request.amount());

        account.setBalance(accountBalanceAfter);
        accountRepository.save(account);

        BigDecimal debtBefore = creditCard.getCurrentDebt();
        BigDecimal debtAfter = debtBefore.subtract(request.amount());

        creditCard.setCurrentDebt(debtAfter);
        creditCardRepository.save(creditCard);

        String description = "Make the credit card debt payment.";

        Transaction transaction = Transaction.builder()
                .accountId(account.getId())
                .cardId(creditCard.getCardId())
                .transactionType(TransactionType.CREDIT_CARD_DEBT_PAYMENT)
                .amount(request.amount())
                .currency(account.getAccountCurrency())
                .balanceBefore(accountBalanceBefore)
                .balanceAfter(accountBalanceAfter)
                .referenceNumber(generateReferenceNumber())
                .description(description)
                .transactionStatus(TransactionStatus.COMPLETED)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return paymentMapper.paymentResponseToTransaction(savedTransaction);
    }

    private String generateReferenceNumber() {
        String referenceNumber;

        do {
            referenceNumber = "ENK-" + UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 12)
                    .toUpperCase();
        } while (transactionRepository.existsByReferenceNumber(referenceNumber));

        return referenceNumber;
    }

    private String generateTransferReference() {
        String transferNumber;

        do {
            transferNumber = "ENK-TSF-" + UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 16)
                    .toUpperCase();
        } while (transactionRepository.existsByTransferReference(transferNumber));

        return transferNumber;
    }

}
