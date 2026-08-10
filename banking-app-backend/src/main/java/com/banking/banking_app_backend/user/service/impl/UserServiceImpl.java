package com.banking.banking_app_backend.user.service.impl;

import com.banking.banking_app_backend.user.dto.request.UserInsertRequest;
import com.banking.banking_app_backend.user.dto.request.UserUpdateRequest;
import com.banking.banking_app_backend.user.dto.response.UserResponse;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.mapper.UserMapper;
import com.banking.banking_app_backend.user.repository.UserRepository;
import com.banking.banking_app_backend.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse newUser(UserInsertRequest userNewInsertRequest) {
        User user = userMapper.newUserToUser(userNewInsertRequest);
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getUserAll() {
        return userRepository.findAll().stream().map(userMapper::userToUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return userMapper.userToUserResponse(user);
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.updateUserFromRequest(request, existingUser);

        User savedUser = userRepository.save(existingUser);
        return userMapper.userToUserResponse(savedUser);
    }

}
