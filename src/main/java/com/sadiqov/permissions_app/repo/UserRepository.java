package com.sadiqov.permissions_app.repo;

import com.sadiqov.permissions_app.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"permissions", "group", "group.permissions"})
    Optional<User> findByUsername(String username);}

