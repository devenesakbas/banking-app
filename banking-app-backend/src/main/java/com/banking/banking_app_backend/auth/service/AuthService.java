package com.banking.banking_app_backend.auth.service;

import com.banking.banking_app_backend.auth.dto.request.LoginRequest;
import com.banking.banking_app_backend.auth.dto.request.RegisterRequest;
import com.banking.banking_app_backend.auth.dto.response.LoginResponse;
import com.banking.banking_app_backend.auth.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);

}
