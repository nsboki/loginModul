package com.ifpro.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpro.login.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findByEmail(String email);

}
