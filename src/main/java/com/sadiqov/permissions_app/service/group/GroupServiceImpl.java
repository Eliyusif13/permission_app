package com.sadiqov.permissions_app.service.group;

import com.sadiqov.permissions_app.dto.request.GroupRequest;
import com.sadiqov.permissions_app.dto.response.GroupResponse;
import com.sadiqov.permissions_app.entity.Group;
import com.sadiqov.permissions_app.entity.Permission;
import com.sadiqov.permissions_app.mapper.GroupMapper;
import com.sadiqov.permissions_app.repo.GroupRepository;
import com.sadiqov.permissions_app.repo.PermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;
    private final GroupMapper mapper;
    private final MessageSource messageSource;

    @Override
    public GroupResponse create(GroupRequest request) {
        if (groupRepository.existsByName(request.name())) {
            throw new IllegalArgumentException();
        }

        Set<Permission> permissions = getPermissionsByIds(request.permissionIds());

        Group group = Group.builder()
                .name(request.name())
                .permissions(permissions)
                .build();

        return mapper.toResponse(groupRepository.save(group));
    }

    @Override
    public GroupResponse update(Long id, GroupRequest request) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("error.notfound",
                                null, LocaleContextHolder.getLocale())
                ));

        group.setName(request.name());
        group.setPermissions(getPermissionsByIds(request.permissionIds()));

        return mapper.toResponse(groupRepository.save(group));
    }

    @Override
    public void delete(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("error.notfound",
                            null, LocaleContextHolder.getLocale())
            );
        }
        groupRepository.deleteById(id);
    }

    @Override
    public GroupResponse getById(Long id) {
        return groupRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("error.notfound", null, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    public List<GroupResponse> getAll() {
        return groupRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    private Set<Permission> getPermissionsByIds(Set<Long> ids) {
        List<Permission> permissions = permissionRepository.findAllById((Iterable<Long>) ids);
        if (permissions.size() != ids.size()) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("error.notfound", null, LocaleContextHolder.getLocale())
            );
        }
        return new HashSet<>(permissions);
    }
}
