package com.sadiqov.permissions_app.dto.response;

import java.util.Set;

public record GroupResponse(
        Long id,
        String name,
        Set<String> permissionNames
) {
}
