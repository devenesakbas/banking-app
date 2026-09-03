package com.banking.banking_app_backend.card.listener;

import com.banking.banking_app_backend.card.service.CardService;
import com.banking.banking_app_backend.common.event.ChangeAccountStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountStatusEventListener {

    private final CardService cardService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void changeAccountStatusRequest(ChangeAccountStatusEvent event) {
        try {
            cardService.updateCardsByAccountStatus(
                    event.getAccountId(),
                    event.getAccountStatus()
            );
        } catch (Exception ex) {
            log.error(
                    "Failed to sync cards for accountId={} to status={}",
                    event.getAccountId(),
                    event.getAccountStatus(),
                    ex
            );
        }
    }
}