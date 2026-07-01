package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.User;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private final IUserRepository userRepository;

	public UserServiceImpl(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public User login(String email, String password) {
		if (email == null || password == null) {
			return null;
		}

		return userRepository.findByEmailIgnoreCase(email.trim())
				.filter(user -> Boolean.TRUE.equals(user.getIsActive()))
				.filter(user -> user.getPasswordHash() != null)
				.filter(user -> user.getPasswordHash().equals(password))
				.orElse(null);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
}
