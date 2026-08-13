package com.banking.banking_app_backend.auth.service.impl;

import com.banking.banking_app_backend.auth.dto.request.ForgotPasswordRequest;
import com.banking.banking_app_backend.auth.dto.request.LoginRequest;
import com.banking.banking_app_backend.auth.dto.request.RefreshRequest;
import com.banking.banking_app_backend.auth.dto.request.RegisterRequest;
import com.banking.banking_app_backend.auth.dto.response.*;
import com.banking.banking_app_backend.auth.entity.PasswordResetCodes;
import com.banking.banking_app_backend.auth.exception.EmailAlreadyExistsException;
import com.banking.banking_app_backend.auth.exception.InvalidCredentialsException;
import com.banking.banking_app_backend.auth.exception.InvalidTokenException;
import com.banking.banking_app_backend.auth.mapper.AuthMapper;
import com.banking.banking_app_backend.auth.repository.PasswordResetCodesRepository;
import com.banking.banking_app_backend.auth.security.JwtService;
import com.banking.banking_app_backend.auth.service.AuthService;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.entity.UserRole;
import com.banking.banking_app_backend.user.exception.UserNotFoundException;
import com.banking.banking_app_backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordResetCodesRepository passwordResetCodesRepository;

    @Value("${reset-password.code-expiration}")
    private final Long ResetCodeExpiration;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already existing.");
        }

        User user = User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.ROLE_USER)
                .build();

        userRepository.save(user);

        return authMapper.userToRegisterResponse(user);

    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Email or password is incorrect."
                ));

        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Email or password is incorrect.");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        Long expiresIn = jwtService.getAccessTokenExpiration();

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();

    }

    @Override
    public RefreshResponse refresh(RefreshRequest request){

        String email = jwtService.extractUsername(request.refreshToken());

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Email or password is incorrect."));

        if(!jwtService.isTokenValid(request.refreshToken(), user)){
            throw new InvalidTokenException("Invalid token.");
        }

        String accessToken = jwtService.generateAccessToken(user);

        return RefreshResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.refreshToken())
                .build();

    }

    @Override
    public MeResponse me(User user) {
        return authMapper.userToMeResponse(user);
    }

    @Override
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request){

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        String code = generatePasswordResetCode();

        PasswordResetCodes passwordResetCodes = PasswordResetCodes.builder()
                .user(user)
                .code(code)
                .expiresAt(LocalDateTime.now().plusSeconds(ResetCodeExpiration))
                .isUsed(false)
                .build();

        PasswordResetCodes resetCode = passwordResetCodesRepository.save(passwordResetCodes);
        return authMapper.passwordResetCodesToForgotPasswordResponse(resetCode);

    }

    private String generatePasswordResetCode() {
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(1000000);
        return String.format("%06d", number);
    }
}
