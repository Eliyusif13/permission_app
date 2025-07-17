package com.sadiqov.permissions_app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record GroupRequest(
        @NotBlank(message = "{group.not.found}")
        String name,

        @NotEmpty(message = "{group.permissions.notEmpty}")
        Set<Long> permissionIds
) {}

