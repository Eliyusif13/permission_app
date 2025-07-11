package com.sadiqov.permissions_app.dto.request;


import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(@NotBlank(message = "{permission.name.notBlank}")
                                String name) {
}
