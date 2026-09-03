package com.banking.banking_app_backend.common.event;

import com.banking.banking_app_backend.account.entity.AccountStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeAccountStatusEvent {

    private final Long accountId;
    private final AccountStatus accountStatus;

}
