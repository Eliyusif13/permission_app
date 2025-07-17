package com.sadiqov.permissions_app.controller;

import com.sadiqov.permissions_app.dto.request.GroupRequest;
import com.sadiqov.permissions_app.dto.response.GroupResponse;
import com.sadiqov.permissions_app.service.group.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/creat")
    public ResponseEntity<GroupResponse> create(@RequestBody @Valid GroupRequest request) {
        return ResponseEntity.ok(groupService.create(request));
    }

    @PreAuthorize("hasAuthority('group.update')")
    @PutMapping("/update/{id}")
    public ResponseEntity<GroupResponse> update(@PathVariable Long id,
                                                @RequestBody @Valid GroupRequest request) {
        return ResponseEntity.ok(groupService.update(id, request));
    }

    @PreAuthorize("hasAuthority('group.delete')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('group.view')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<GroupResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @PreAuthorize("hasAuthority('group.view')")
    @GetMapping("/getAll")
    public ResponseEntity<List<GroupResponse>> getAll() {
        return ResponseEntity.ok(groupService.getAll());
    }
}
