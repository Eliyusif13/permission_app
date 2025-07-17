package com.sadiqov.permissions_app.service.user;

import com.sadiqov.permissions_app.config.jwt.JwtService;
import com.sadiqov.permissions_app.dto.request.LoginRequest;
import com.sadiqov.permissions_app.dto.request.RegisterRequest;
import com.sadiqov.permissions_app.dto.request.UserRequest;
import com.sadiqov.permissions_app.dto.response.AuthResponse;
import com.sadiqov.permissions_app.dto.response.UserResponse;
import com.sadiqov.permissions_app.entity.Group;
import com.sadiqov.permissions_app.entity.Permission;
import com.sadiqov.permissions_app.entity.User;
import com.sadiqov.permissions_app.mapper.UserMapper;
import com.sadiqov.permissions_app.repo.GroupRepository;
import com.sadiqov.permissions_app.repo.PermissionRepository;
import com.sadiqov.permissions_app.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    @Autowired
    MessageSource messageSource;



    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("user.not.found",
                        null, LocaleContextHolder.getLocale())));

        user.setUsername(request.username());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        Group group = groupRepository.findById(request.groupId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("group.not.found",
                        null, LocaleContextHolder.getLocale())));
        user.setGroup(group);

        if (request.permissionIds() != null && !request.permissionIds().isEmpty()) {
            Set<Permission> permissions = permissionRepository.findAllById(request.permissionIds());
            if (permissions.size() != request.permissionIds().size()) {
                throw new EntityNotFoundException("Some permissions not found");
            }
            user.setPermissions(permissions);
        }
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException(messageSource.getMessage("user.not.found",
                    null, LocaleContextHolder.getLocale()));
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("user.not.found",
                        null, LocaleContextHolder.getLocale())));
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new EntityNotFoundException(messageSource.getMessage("error.alreadyExists",
                    null, LocaleContextHolder.getLocale()));
        }

        if (request.password().length() < 8) {
            throw new EntityNotFoundException(messageSource.getMessage("password.required.regexp",
                    null, LocaleContextHolder.getLocale()));
        }

        if (userRepository.existsByEmail(request.email()))
            throw new EntityNotFoundException(messageSource.getMessage("error.alreadyExists",
                    null, LocaleContextHolder.getLocale()));

        Group group = groupRepository.findById(
                        request.groupIds().stream().findFirst().orElseThrow(
                                () -> new EntityNotFoundException(messageSource.getMessage("error.notfound",
                                        null, LocaleContextHolder.getLocale()))))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("group.not.found",
                        null, LocaleContextHolder.getLocale())));


        Set<Permission> permissions = new HashSet<>();
        if (request.permissionIds() != null) {
            permissions = request.permissionIds().stream()
                    .map(id -> permissionRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + id)))
                    .collect(Collectors.toSet());
        }
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .group(group)
                .permissions(permissions)
                .build();
        userRepository.save(user);

        return new AuthResponse("User registered successfully");


    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .groupName(user.getGroup() != null ? user.getGroup().getName() : null)
                .permissions(user.getPermissions().stream()
                        .map(Permission::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

}
