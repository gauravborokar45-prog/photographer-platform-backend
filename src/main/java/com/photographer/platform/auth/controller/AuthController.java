package com.photographer.platform.auth.controller;

import com.photographer.platform.common.response.ApiResponse;
import com.photographer.platform.user.dto.request.LoginRequest;
import com.photographer.platform.user.dto.request.RegisterRequest;
import com.photographer.platform.user.dto.response.LoginResponse;
import com.photographer.platform.user.dto.response.UserResponse;
import com.photographer.platform.user.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register User
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse user = userService.register(request);

        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User registered successfully.")
                .data(user)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login User
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse loginResponse = userService.login(request);

        ApiResponse<LoginResponse> response = ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login Successful")
                .data(loginResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}