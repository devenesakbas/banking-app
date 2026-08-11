package com.banking.banking_app_backend.user.service.impl;

import com.banking.banking_app_backend.user.dto.request.UserInsertRequest;
import com.banking.banking_app_backend.user.dto.request.UserUpdateRequest;
import com.banking.banking_app_backend.user.dto.response.UserResponse;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.exception.UserNotFoundException;
import com.banking.banking_app_backend.user.mapper.UserMapper;
import com.banking.banking_app_backend.user.repository.UserRepository;
import com.banking.banking_app_backend.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserResponse newUser(UserInsertRequest userNewInsertRequest) {
        User user = userMapper.newUserToUser(userNewInsertRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getUserAll() {
        return userRepository.findAll().stream().map(userMapper::userToUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return userMapper.userToUserResponse(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userMapper.updateUserFromRequest(request, existingUser);

        User savedUser = userRepository.save(existingUser);
        return userMapper.userToUserResponse(savedUser);
    }

}
