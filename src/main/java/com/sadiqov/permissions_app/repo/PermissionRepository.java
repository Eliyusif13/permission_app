package com.sadiqov.permissions_app.repo;
import com.sadiqov.permissions_app.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
    @Query("SELECT p FROM Permission p WHERE p.id IN :ids")
    Set<Permission> findAllById(@Param("ids") Set<Long> ids);
}
