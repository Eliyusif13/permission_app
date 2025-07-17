package com.sadiqov.permissions_app.controller;


import com.sadiqov.permissions_app.dto.request.PermissionRequest;
import com.sadiqov.permissions_app.dto.response.PermissionResponse;
import com.sadiqov.permissions_app.service.permission.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService service;

    @PostMapping("/creat")
    public ResponseEntity<PermissionResponse> create(@RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasAuthority('user.update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PermissionResponse> update(@PathVariable Long id,
                                                     @RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PreAuthorize("hasAuthority('user.delete')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping("getById/{id}")
    public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping("/getAll")
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
