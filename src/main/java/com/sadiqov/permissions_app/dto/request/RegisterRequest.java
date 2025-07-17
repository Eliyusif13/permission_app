package com.sadiqov.permissions_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequest(
        String username,
        String email,

        @Size(min = 6, message = "password.required.regexp")
        String password,
        @NotNull
        Set<Long> groupIds,
        @NotNull
        Set<Long> permissionIds

) {
}