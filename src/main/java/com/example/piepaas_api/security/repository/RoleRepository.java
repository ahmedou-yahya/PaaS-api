package com.example.piepaas_api.security.repository;

import com.example.piepaas_api.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByName(String name);
}
