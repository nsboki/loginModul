package com.ifpro.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpro.login.model.User;
import com.ifpro.login.repository.UserRepository;

import groovy.util.logging.Log4j;

@Service
@Transactional
@Log4j
public class UserServiceImpl implements UserService {

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

}
