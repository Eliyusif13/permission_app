package com.sadiqov.permissions_app.dto.response;

import lombok.Builder;

@Builder
public record AuthResponseLogin(
        String message
) {
}
