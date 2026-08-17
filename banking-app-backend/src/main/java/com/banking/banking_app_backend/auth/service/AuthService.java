package com.banking.banking_app_backend.auth.service;

import com.banking.banking_app_backend.auth.dto.request.*;
import com.banking.banking_app_backend.auth.dto.response.*;
import com.banking.banking_app_backend.common.response.ApiResponse;
import com.banking.banking_app_backend.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);

    RefreshResponse refresh(RefreshRequest refreshRequest);

    MeResponse me(User user);

    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    VerifyResetCodeResponse verifyResetCode(VerifyResetCodeRequest request);

    ResetPasswordResponse resetPassword(ResetPasswordRequest request);

}
