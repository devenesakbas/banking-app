package com.banking.banking_app_backend.notification.listener;

import com.banking.banking_app_backend.common.event.PasswordResetSendMailEvent;
import com.banking.banking_app_backend.notification.dto.request.ResetCodeRequest;
import com.banking.banking_app_backend.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final EmailService emailService;

    @Async
    @EventListener
    public void handlePasswordResetRequest(PasswordResetSendMailEvent event){

        ResetCodeRequest requestSendMail = ResetCodeRequest.builder()
                .to(event.getEmail())
                .code(event.getCode())
                .build();

        emailService.sendResetCodeEmail(requestSendMail);

    }

}
