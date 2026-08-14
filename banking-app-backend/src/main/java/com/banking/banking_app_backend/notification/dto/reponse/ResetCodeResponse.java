package com.banking.banking_app_backend.notification.dto.reponse;

import lombok.Builder;

@Builder
public record ResetCodeResponse(
        boolean send
) {
}
