package com.sadiqov.permissions_app.controller;

import com.sadiqov.permissions_app.dto.request.LoginRequest;
import com.sadiqov.permissions_app.dto.request.RegisterRequest;
import com.sadiqov.permissions_app.dto.request.UserRequest;
import com.sadiqov.permissions_app.dto.response.AuthResponse;
import com.sadiqov.permissions_app.dto.response.AuthResponseLogin;
import com.sadiqov.permissions_app.dto.response.UserResponse;
import com.sadiqov.permissions_app.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<AuthResponseLogin> create(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PreAuthorize("hasAuthority('user.update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody  UserRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PreAuthorize("hasAuthority('user.delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PreAuthorize("hasAuthority('user.read')")
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}

