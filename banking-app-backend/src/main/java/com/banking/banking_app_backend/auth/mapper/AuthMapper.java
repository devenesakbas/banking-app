package com.banking.banking_app_backend.auth.mapper;

import com.banking.banking_app_backend.auth.dto.request.ForgotPasswordRequest;
import com.banking.banking_app_backend.auth.dto.request.RegisterRequest;
import com.banking.banking_app_backend.auth.dto.response.ForgotPasswordResponse;
import com.banking.banking_app_backend.auth.dto.response.LoginResponse;
import com.banking.banking_app_backend.auth.dto.response.MeResponse;
import com.banking.banking_app_backend.auth.dto.response.RegisterResponse;
import com.banking.banking_app_backend.auth.entity.PasswordResetCodes;
import com.banking.banking_app_backend.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User registerRequestToUser(RegisterRequest registerRequest);
    RegisterResponse userToRegisterResponse(User user);
    MeResponse userToMeResponse(User user);
    PasswordResetCodes forgotPasswordRequestToPasswordResetCodes(ForgotPasswordRequest forgotPasswordRequest);
    ForgotPasswordResponse passwordResetCodesToForgotPasswordResponse(PasswordResetCodes passwordResetCodes);

}
