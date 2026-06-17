package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.User;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements IUserService {
	private final IUserRepository userRepository;

	public UserImpl(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> findAll() {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve users", e);
		}
	}

	@Override
	public User findById(Integer id) {
		try {
			return userRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve user by id", e);
		}
	}

	@Override
	public void save(User user) {
		try {
			userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save user", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete user", e);
		}
	}
}