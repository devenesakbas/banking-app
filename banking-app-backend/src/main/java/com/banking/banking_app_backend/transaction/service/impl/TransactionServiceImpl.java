package com.banking.banking_app_backend.transaction.service.impl;

import com.banking.banking_app_backend.account.entity.Account;
import com.banking.banking_app_backend.account.entity.AccountCurrency;
import com.banking.banking_app_backend.account.entity.AccountStatus;
import com.banking.banking_app_backend.account.exception.AccountNotFoundException;
import com.banking.banking_app_backend.account.exception.IllegalArgumentException;
import com.banking.banking_app_backend.account.exception.InvalidAccountException;
import com.banking.banking_app_backend.account.exception.UnauthorizedAccountAccessException;
import com.banking.banking_app_backend.account.repository.AccountRepository;
import com.banking.banking_app_backend.auth.security.SecurityUtils;
import com.banking.banking_app_backend.transaction.dto.request.TransactionDepositRequest;
import com.banking.banking_app_backend.transaction.dto.request.TransactionTransferRequest;
import com.banking.banking_app_backend.transaction.dto.request.TransactionWithdrawRequest;
import com.banking.banking_app_backend.transaction.dto.response.TransactionResponse;
import com.banking.banking_app_backend.transaction.entity.Transaction;
import com.banking.banking_app_backend.transaction.entity.TransactionStatus;
import com.banking.banking_app_backend.transaction.entity.TransactionType;
import com.banking.banking_app_backend.transaction.exception.BalanceErrorException;
import com.banking.banking_app_backend.transaction.exception.TransactionNotFoundException;
import com.banking.banking_app_backend.transaction.exception.UnauthorizedTransactionAccessException;
import com.banking.banking_app_backend.transaction.mapper.TransactionMapper;
import com.banking.banking_app_backend.transaction.repository.TransactionRepository;
import com.banking.banking_app_backend.transaction.service.TransactionService;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.entity.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    @Override
    public List<TransactionResponse> getAllTransaction() {
        User user = SecurityUtils.getCurrentUser();
        List<Transaction> transactions;

        if (user.getRole() == UserRole.ROLE_SUPER_ADMIN) {
            transactions = transactionRepository.findAll();
        } else {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());
            transactions = transactionRepository.findAllByAccountIdIn(byAccountIds);
        }

        return transactions
                .stream()
                .map(transactionMapper::transactionToTransactionResponse)
                .toList();
    }

    @Override
    public TransactionResponse getTransaction(Long id) {
        User user = SecurityUtils.getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());

            if (user.getRole() == UserRole.ROLE_USER && !byAccountIds.contains(transaction.getAccountId())) {
                throw new UnauthorizedTransactionAccessException("Unauthorizated transaction access");
            }
        }

        return transactionMapper.transactionToTransactionResponse(transaction);

    }

    @Override
    public List<TransactionResponse> getTransactionAllByAccountId(Long id) {
        User user = SecurityUtils.getCurrentUser();

        if (user.getRole() != UserRole.ROLE_SUPER_ADMIN) {
            List<Long> byAccountIds = getAccountIdsByUserId(user.getId());

            if (!byAccountIds.contains(id)) {
                throw new UnauthorizedTransactionAccessException("Unauthorizated transaction access");
            }
        }

        List<Transaction> transactions = transactionRepository.findAllByAccountId(id);

        return transactions
                .stream()
                .map(transactionMapper::transactionToTransactionResponse)
                .toList();

    }

    @Transactional
    @Override
    public TransactionResponse handleDeposit(TransactionDepositRequest request){

        User user = SecurityUtils.getCurrentUser();
        Account account = accountRepository.findByIdForUpdate(request.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(!account.getUserId().equals(user.getId())){
            throw new UnauthorizedAccountAccessException("Unauthorized account access");
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new InvalidAccountException("Account is not active");
        }

        if(account.getAccountCurrency() != request.currency()){
            throw new InvalidAccountException("Account currency does not match transaction currency");
        }

        if(request.amount().compareTo(BigDecimal.ZERO) <= 0){
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

        return transactionMapper.transactionToTransactionResponse(newTransaction);
    }

    @Transactional
    @Override
    public TransactionResponse handleWithdraw(TransactionWithdrawRequest request){
        User user = SecurityUtils.getCurrentUser();

        Account account = accountRepository.findByIdForUpdate(request.accountId())
                .orElseThrow(()-> new AccountNotFoundException("Account not found"));

        if(!account.getUserId().equals(user.getId())){
            throw new UnauthorizedTransactionAccessException("Unauthorization transaction access");
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new InvalidAccountException("Account is not active");
        }

        if(!account.getAccountCurrency().equals(request.currency())){
            throw new InvalidAccountException("Account currency does not match transaction currency");
        }

        if(request.amount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }

        if(account.getBalance().compareTo(request.amount()) < 0 ){
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

        return transactionMapper.transactionToTransactionResponse(newTransaction);

    }

    @Transactional
    @Override
    public TransactionResponse handleTransfer(TransactionTransferRequest request){
        User user = SecurityUtils.getCurrentUser();

        Account sourceAccount = accountRepository.findByIdForUpdate(request.sourceAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));

        Account destinationAccount = accountRepository.findByIdForUpdate(request.destinationAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

        if(!sourceAccount.getUserId().equals(user.getId())){
            throw new UnauthorizedAccountAccessException("Unauthorized account access");
        }

        if(sourceAccount.getAccountStatus() != AccountStatus.ACTIVE){
            throw new InvalidAccountException("Source account is not active");
        }

        if(destinationAccount.getAccountStatus() != AccountStatus.ACTIVE){
            throw new InvalidAccountException("Destination account is not active");
        }

        if(sourceAccount.getId().equals(destinationAccount.getId())){
            throw new InvalidAccountException("Invalid source and destination account");
        }

        if(sourceAccount.getAccountCurrency() != request.currency()){
            throw new InvalidAccountException("Account currency does not match transaction currency");
        }

        if (destinationAccount.getAccountCurrency() != request.currency()) {
            throw new InvalidAccountException(
                    "Destination account currency does not match transaction currency"
            );
        }

        if(request.amount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }

        if(sourceAccount.getBalance().compareTo(request.amount()) < 0){
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

        return transactionMapper.transactionToTransactionResponse(transaction);

    }

    private List<Long> getAccountIdsByUserId(Long userId) {
        return accountRepository.findAllByUserId(userId)
                .stream()
                .map(Account::getId)
                .toList();
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