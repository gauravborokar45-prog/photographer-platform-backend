package com.photographer.platform.auth.controller;

import com.photographer.platform.common.response.ApiResponse;
import com.photographer.platform.user.dto.request.LoginRequest;
import com.photographer.platform.user.dto.request.RegisterRequest;
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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse user = userService.register(request);

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("User registered successfully.");
        response.setData(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        UserResponse user = userService.login(request);

        ApiResponse<UserResponse> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setMessage("Login successful");
        response.setData(user);

        return ResponseEntity.ok(response);
    }
}