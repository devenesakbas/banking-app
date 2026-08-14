package com.banking.banking_app_backend.notification.service;

import com.banking.banking_app_backend.notification.dto.reponse.ResetCodeResponse;
import com.banking.banking_app_backend.notification.dto.request.ResetCodeRequest;

public interface EmailService {

    ResetCodeResponse sendResetCodeEmail(ResetCodeRequest request);

}
