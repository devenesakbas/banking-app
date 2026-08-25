package com.banking.banking_app_backend.common.event;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PasswordResetSendMailEvent {

    private final String email;
    private final String code;

}
