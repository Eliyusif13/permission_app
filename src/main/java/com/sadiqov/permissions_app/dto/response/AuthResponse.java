package com.sadiqov.permissions_app.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token
) {
}
