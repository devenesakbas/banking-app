package com.banking.banking_app_backend.auth.controller;

import com.banking.banking_app_backend.auth.dto.request.*;
import com.banking.banking_app_backend.auth.dto.response.*;
import com.banking.banking_app_backend.auth.service.AuthService;
import com.banking.banking_app_backend.common.response.ApiResponse;
import com.banking.banking_app_backend.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<ForgotPasswordResponse>> forgotpassword(@RequestBody @Valid ForgotPasswordRequest request){
        ForgotPasswordResponse result = authService.forgotPassword(request);
        return ResponseEntity.ok(
                ApiResponse.success(result, "Forgot password successful")
        );
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<ApiResponse<VerifyResetCodeResponse>> verifyResetCode(@RequestBody @Valid VerifyResetCodeRequest request){
        VerifyResetCodeResponse result = authService.verifyResetCode(request);

        return ResponseEntity.ok(
                ApiResponse.success(result, "Code verified successfully")
        );
    }

}
