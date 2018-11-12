package com.ifpro.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpro.login.model.security.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(String name);
}
