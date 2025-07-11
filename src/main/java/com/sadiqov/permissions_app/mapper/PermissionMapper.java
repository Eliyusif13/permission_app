package com.sadiqov.permissions_app.mapper;


import com.sadiqov.permissions_app.dto.request.PermissionRequest;
import com.sadiqov.permissions_app.dto.response.PermissionResponse;
import com.sadiqov.permissions_app.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface PermissionMapper {

    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    Permission toEntity(PermissionRequest dto);

    PermissionResponse toResponse(Permission entity);
}

