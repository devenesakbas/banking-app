package com.banking.banking_app_backend.user.controller;

import com.banking.banking_app_backend.user.dto.request.UserInsertRequest;
import com.banking.banking_app_backend.user.dto.request.UserUpdateRequest;
import com.banking.banking_app_backend.user.dto.response.UserResponse;
import com.banking.banking_app_backend.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getUserAll() {
        return userService.getUserAll();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserResponse newUser(@RequestBody @Valid UserInsertRequest user){
        return userService.newUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public UserResponse patchUser(@PathVariable Long id, @RequestBody UserUpdateRequest user){
        return userService.updateUser(id, user);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest user){
        return userService.updateUser(id, user);
    }
}
