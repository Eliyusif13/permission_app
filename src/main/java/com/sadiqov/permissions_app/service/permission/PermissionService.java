package com.sadiqov.permissions_app.service.permission;

import com.sadiqov.permissions_app.dto.request.PermissionRequest;
import com.sadiqov.permissions_app.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    PermissionResponse update(Long id, PermissionRequest request);

    void delete(Long id);

    PermissionResponse getById(Long id);

    List<PermissionResponse> getAll();
}

