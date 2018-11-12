package com.ifpro.login.service;

import com.ifpro.login.model.User;

public interface UserService {

	User findByUsername(String username);

	User findByEmail(String email);

	boolean checkUserExists(String username, String email);

	boolean checkUsernameExists(String username);

	boolean checkEmailExists(String email);

	void save(User user);

	void enableUser(String username);

	void disableUser(String username);

	User createUser(User user);

	User saveUser(User user);
}
