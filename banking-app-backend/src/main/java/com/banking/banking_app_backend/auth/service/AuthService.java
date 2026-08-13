package com.banking.banking_app_backend.auth.service;

import com.banking.banking_app_backend.auth.dto.request.ForgotPasswordRequest;
import com.banking.banking_app_backend.auth.dto.request.LoginRequest;
import com.banking.banking_app_backend.auth.dto.request.RefreshRequest;
import com.banking.banking_app_backend.auth.dto.request.RegisterRequest;
import com.banking.banking_app_backend.auth.dto.response.*;
import com.banking.banking_app_backend.user.entity.User;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);

    RefreshResponse refresh(RefreshRequest refreshRequest);

    MeResponse me(User user);

    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
}
