package com.sadiqov.permissions_app.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(

        @NotBlank(message = "username.required")
        String username,

        @NotBlank(message = "password.required")
        String password,

        @NotNull(message = "groupId.required")
        Long groupId
) {}
