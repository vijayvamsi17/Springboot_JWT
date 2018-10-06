package com.jwtAuthentication.jwtAuthentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtAuthentication.jwtAuthentication.model.Role;
import com.jwtAuthentication.jwtAuthentication.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName roleName);
}
