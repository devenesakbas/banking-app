package com.banking.banking_app_backend.user.service;

import com.banking.banking_app_backend.user.dto.response.UserResponse;
import com.banking.banking_app_backend.user.entity.User;
import com.banking.banking_app_backend.user.exception.UserNotFoundException;
import com.banking.banking_app_backend.user.mapper.UserMapper;
import com.banking.banking_app_backend.user.repository.UserRepository;
import com.banking.banking_app_backend.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("nameTest");
        user.setSurname("surnameTest");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setCreatedAt(LocalDateTime.now());

        userResponse = new UserResponse(1L, "nameTest", "surnameTest", "test@example.com", LocalDateTime.now());
    }


    @Test
    void getUserById_ShouldReturnUserResponse_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(userRepository, times(1)).findById(1L);

    }

    @Test
    void getUserById_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(99L);
        });

        verify(userRepository, times(1)).findById(99L);

    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).deleteById(1L);

    }

}
