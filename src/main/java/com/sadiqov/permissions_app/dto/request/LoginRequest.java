package com.sadiqov.permissions_app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "username.required")
        String username,

        @NotBlank(message = "password.required")
        String password
) {}
