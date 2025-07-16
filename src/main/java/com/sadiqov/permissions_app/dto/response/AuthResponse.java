package com.sadiqov.permissions_app.dto.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record AuthResponse(
        String token,
        String message,
        String username,
        String groupName,
        Set<String> permissions
) {
    public AuthResponse(String message) {
        this(null, message, null, null, null);
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }


}