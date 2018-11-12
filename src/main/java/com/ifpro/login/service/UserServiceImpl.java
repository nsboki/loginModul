package com.ifpro.login.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpro.login.model.User;
import com.ifpro.login.model.security.UserRole;
import com.ifpro.login.repository.RoleRepository;
import com.ifpro.login.repository.UserRepository;

import groovy.util.logging.Log4j;

@Service
@Transactional
@Log4j
public class UserServiceImpl implements UserService {

	/**
	 * The application logger
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(UserSecurityService.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean checkUserExists(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(email)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkUsernameExists(String username) {
		if (findByUsername(username) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkEmailExists(String email) {
		if (findByEmail(email) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void enableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(true);
		userRepository.save(user);
	}

	@Override
	public void disableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(false);
		userRepository.save(user);
	}

	@Override
	public User createUser(User user) {
		User localUser = userRepository.findByUsername(user.getUsername());
		if (localUser != null) {
			LOG.info("User with username {} already exist. Nothing will be done. ",
					user.getUsername());
		} else {
			encodePassword(user);
			setDefaultRole(user);
			localUser = userRepository.save(user);
		}

		return localUser;
	}

	private void encodePassword(User user) {
		String encriptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encriptedPassword);
	}

	private void setDefaultRole(User user) {
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, roleRepository.findByName("ROLE_USER")));
		for (UserRole userRole : userRoles) {
			roleRepository.save(userRole.getRole());
		}
		user.getUserRoles().addAll(userRoles);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

}
