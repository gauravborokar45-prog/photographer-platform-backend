package com.photographer.platform.user.service;

import com.photographer.platform.user.dto.request.RegisterRequest;
import com.photographer.platform.user.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

}