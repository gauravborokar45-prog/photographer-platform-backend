package com.photographer.platform.photographer.controller;

import com.photographer.platform.common.response.ApiResponse;
import com.photographer.platform.photographer.dto.request.CreatePhotographerRequest;
import com.photographer.platform.photographer.dto.response.PhotographerResponse;
import com.photographer.platform.photographer.service.PhotographerService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/photographers")
public class PhotographerController {

    private final PhotographerService photographerService;

    public PhotographerController(PhotographerService photographerService) {
        this.photographerService = photographerService;
    }

    /**
     * Create Photographer Profile
     */
    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<PhotographerResponse>> createProfile(
            Authentication authentication,
            @Valid @RequestBody CreatePhotographerRequest request) {

        String email = authentication.getName();

        PhotographerResponse response =
                photographerService.createProfile(email, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<PhotographerResponse>builder()
                                .success(true)
                                .message("Photographer profile created successfully.")
                                .data(response)
                                .build()
                );
    }

    /**
     * Get Logged-in Photographer Profile
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<PhotographerResponse>> getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        PhotographerResponse response =
                photographerService.getProfile(email);

        return ResponseEntity.ok(
                ApiResponse.<PhotographerResponse>builder()
                        .success(true)
                        .message("Photographer profile fetched successfully.")
                        .data(response)
                        .build()
        );
    }
}