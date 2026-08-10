package com.banking.banking_app_backend.user.controller;

import com.banking.banking_app_backend.common.response.ApiResponse;
import com.banking.banking_app_backend.user.dto.request.UserInsertRequest;
import com.banking.banking_app_backend.user.dto.request.UserUpdateRequest;
import com.banking.banking_app_backend.user.dto.response.UserResponse;
import com.banking.banking_app_backend.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUserAll() {
        List<UserResponse> response = userService.getUserAll();
        return ResponseEntity.ok(
                ApiResponse.success(response, "Users fetched successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(
                ApiResponse.success(response, "User fetched successfully")
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> newUser(@RequestBody @Valid UserInsertRequest user) {
        UserResponse response = userService.newUser(user);
        return ResponseEntity.ok(
                ApiResponse.success(response, "User created successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.success("User deleted successfully")
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> patchUser(@PathVariable Long id, @RequestBody UserUpdateRequest user) {
        UserResponse response = userService.updateUser(id, user);
        return ResponseEntity.ok(
                ApiResponse.success(response, "User updated successfully")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest user) {
        UserResponse response = userService.updateUser(id, user);
        return ResponseEntity.ok(
                ApiResponse.success(response, "User updated successfully")
        );
    }
}
