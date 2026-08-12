package com.banking.banking_app_backend.auth.security;

import com.banking.banking_app_backend.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    Long getAccessTokenExpiration();

    Long getRefreshTokenExpiration();

}
