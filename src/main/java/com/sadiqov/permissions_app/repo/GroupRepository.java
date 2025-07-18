package com.sadiqov.permissions_app.repo;

import com.sadiqov.permissions_app.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);

    boolean existsByName(String name);
}
