package com.sadiqov.permissions_app.mapper;

import com.sadiqov.permissions_app.dto.request.RegisterRequest;
import com.sadiqov.permissions_app.dto.request.UserRequest;
import com.sadiqov.permissions_app.dto.response.UserResponse;
import com.sadiqov.permissions_app.entity.Group;
import com.sadiqov.permissions_app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "groups", expression = "java(Set.of(user.getGroup().getName()))")
    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    User toUser(UserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", source = "group")
    User toUser(RegisterRequest request, Group group);
}
