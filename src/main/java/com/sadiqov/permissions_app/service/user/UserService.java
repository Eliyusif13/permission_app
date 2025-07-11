package com.sadiqov.permissions_app.service.user;


import com.sadiqov.permissions_app.dto.request.LoginRequest;
import com.sadiqov.permissions_app.dto.request.RegisterRequest;
import com.sadiqov.permissions_app.dto.request.UserRequest;
import com.sadiqov.permissions_app.dto.response.AuthResponse;
import com.sadiqov.permissions_app.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(UserRequest request);

    UserResponse update(Long id, UserRequest request);

    void delete(Long id);

    UserResponse getById(Long id);

    List<UserResponse> getAll();

    AuthResponse register(RegisterRequest request);

     AuthResponse login(LoginRequest request);

}

