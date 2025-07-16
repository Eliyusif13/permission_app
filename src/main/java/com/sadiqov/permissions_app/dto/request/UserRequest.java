package com.sadiqov.permissions_app.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserRequest(

        @NotBlank(message = "username.required")
        String username,

        @NotBlank(message = "password.required.regexp")
        String password,

        @NotNull(message = "groupId.required")
        Long groupId,

        Set<Long> permissionIds
) {}
