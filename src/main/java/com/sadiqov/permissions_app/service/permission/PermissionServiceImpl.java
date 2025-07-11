package com.sadiqov.permissions_app.service.permission;

import com.sadiqov.permissions_app.dto.request.PermissionRequest;
import com.sadiqov.permissions_app.dto.response.PermissionResponse;
import com.sadiqov.permissions_app.entity.Permission;
import com.sadiqov.permissions_app.mapper.PermissionMapper;
import com.sadiqov.permissions_app.repo.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;
    private final PermissionMapper mapper;
    private final MessageSource messageSource;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        if (repository.existsByName(request.name())) {
            throw new IllegalArgumentException();
        }

        Permission permission = mapper.toEntity(request);
        return mapper.toResponse(repository.save(permission));
    }

    @Override
    public PermissionResponse update(Long id, PermissionRequest request) {
        Permission existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("error.notfound",
                                null, LocaleContextHolder.getLocale())
                ));

        existing.setName(request.name());
        return mapper.toResponse(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("error.notfound",
                            null, LocaleContextHolder.getLocale())
            );
        }
        repository.deleteById(id);
    }

    @Override
    public PermissionResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("error.notfound",
                                null, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public List<PermissionResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
}

