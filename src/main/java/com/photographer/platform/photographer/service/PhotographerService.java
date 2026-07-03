package com.photographer.platform.photographer.service;

import com.photographer.platform.photographer.dto.request.CreatePhotographerRequest;
import com.photographer.platform.photographer.dto.response.PhotographerResponse;

public interface PhotographerService {

    PhotographerResponse createProfile(
            String email,
            CreatePhotographerRequest request
    );

    PhotographerResponse getProfile(
            String email
    );

}