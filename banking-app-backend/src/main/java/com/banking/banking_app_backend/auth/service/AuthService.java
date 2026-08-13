package com.banking.banking_app_backend.auth.service;

import com.banking.banking_app_backend.auth.dto.request.LoginRequest;
import com.banking.banking_app_backend.auth.dto.request.RefreshRequest;
import com.banking.banking_app_backend.auth.dto.request.RegisterRequest;
import com.banking.banking_app_backend.auth.dto.response.LoginResponse;
import com.banking.banking_app_backend.auth.dto.response.MeResponse;
import com.banking.banking_app_backend.auth.dto.response.RefreshResponse;
import com.banking.banking_app_backend.auth.dto.response.RegisterResponse;
import com.banking.banking_app_backend.user.entity.User;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);

    RefreshResponse refresh(RefreshRequest refreshRequest);

    MeResponse me(User user);
}
