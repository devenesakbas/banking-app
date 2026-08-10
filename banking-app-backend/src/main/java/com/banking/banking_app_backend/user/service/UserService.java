package com.banking.banking_app_backend.user.service;

import com.banking.banking_app_backend.user.dto.request.UserInsertRequest;
import com.banking.banking_app_backend.user.dto.request.UserUpdateRequest;
import com.banking.banking_app_backend.user.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getUserAll();
    UserResponse newUser(UserInsertRequest userNewInsertRequest);
    UserResponse getUserById(Long id);
    void deleteUser(Long id);
    UserResponse updateUser(Long id, UserUpdateRequest user);

}
