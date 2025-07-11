package com.sadiqov.permissions_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequest(
        @NotBlank(message = "{validation.user.username.notblank}")
        @Size(min = 3, max = 20, message = "{validation.user.username.size}")
        String username,

        @Email(message = "{validation.user.email.invalid}")
        @NotBlank(message = "{validation.user.email.notblank}")
        String email,

        @Size(min = 6, message = "{validation.user.password.size}")
        String password,
        @NotNull
        Set<Long> groupIds,
        Set<Long> permissionIds

) {
}