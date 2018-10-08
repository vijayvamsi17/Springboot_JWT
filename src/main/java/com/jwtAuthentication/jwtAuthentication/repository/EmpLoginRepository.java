package com.jwtAuthentication.jwtAuthentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtAuthentication.jwtAuthentication.model.EmployeeLogin;

public interface EmpLoginRepository extends JpaRepository<EmployeeLogin, Long> {
	
	Optional<EmployeeLogin> findByUsername(String username);
}
