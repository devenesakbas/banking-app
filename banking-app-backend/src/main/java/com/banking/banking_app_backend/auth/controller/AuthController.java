package com.banking.banking_app_backend.auth.controller;

import com.banking.banking_app_backend.auth.dto.request.LoginRequest;
import com.banking.banking_app_backend.auth.dto.request.MeRequest;
import com.banking.banking_app_backend.auth.dto.request.RefreshRequest;
import com.banking.banking_app_backend.auth.dto.request.RegisterRequest;
import com.banking.banking_app_backend.auth.dto.response.LoginResponse;
import com.banking.banking_app_backend.auth.dto.response.MeResponse;
import com.banking.banking_app_backend.auth.dto.response.RefreshResponse;
import com.banking.banking_app_backend.auth.dto.response.RegisterResponse;
import com.banking.banking_app_backend.auth.service.AuthService;
import com.banking.banking_app_backend.common.response.ApiResponse;
import com.banking.banking_app_backend.user.dto.response.UserResponse;
import com.banking.banking_app_backend.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse result = authService.register(request);
        return ResponseEntity.ok(
                ApiResponse.success(result, "Register successful")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse result = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.success(result, "Login successful")
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshResponse>> refresh(@RequestBody @Valid RefreshRequest request){
        RefreshResponse result = authService.refresh(request);
        return ResponseEntity.ok(
                ApiResponse.success(result, "Refresh successful")
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<MeResponse>> me(@AuthenticationPrincipal User user){
        MeResponse result = authService.me(user);
        return ResponseEntity.ok(
                ApiResponse.success(result, "Me successful")
        );
    }

}
