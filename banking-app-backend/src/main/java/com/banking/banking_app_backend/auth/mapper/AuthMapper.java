package com.banking.banking_app_backend.auth.mapper;

import com.banking.banking_app_backend.auth.dto.request.RegisterRequest;
import com.banking.banking_app_backend.auth.dto.response.LoginResponse;
import com.banking.banking_app_backend.auth.dto.response.MeResponse;
import com.banking.banking_app_backend.auth.dto.response.RegisterResponse;
import com.banking.banking_app_backend.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User registerRequestToUser(RegisterRequest registerRequest);
    RegisterResponse userToRegisterResponse(User user);
    MeResponse userToMeResponse(User user);

}
