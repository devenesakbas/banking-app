package com.banking.banking_app_backend.user.mapper;

import com.banking.banking_app_backend.user.dto.request.UserLoginRequest;
import com.banking.banking_app_backend.user.dto.request.UserInsertRequest;
import com.banking.banking_app_backend.user.dto.request.UserUpdateRequest;
import com.banking.banking_app_backend.user.dto.response.UserResponse;
import com.banking.banking_app_backend.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User newUserToUser(UserInsertRequest userNewInsertRequest);
    UserResponse userToUserResponse(User user);
    User useUpdateToUser(UserUpdateRequest userUpdateRequest);
    UserLoginRequest userToUserLoginRequest(User user);

    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User existingUser);
}
