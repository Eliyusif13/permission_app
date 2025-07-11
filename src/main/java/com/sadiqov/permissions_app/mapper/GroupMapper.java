package com.sadiqov.permissions_app.mapper;

import com.sadiqov.permissions_app.dto.response.GroupResponse;
import com.sadiqov.permissions_app.entity.Group;
import com.sadiqov.permissions_app.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "permissionNames", expression = "java(mapPermissions(group))")
    GroupResponse toResponse(Group group);

    default Set<String> mapPermissions(Group group) {
        return group.getPermissions()
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }
}
