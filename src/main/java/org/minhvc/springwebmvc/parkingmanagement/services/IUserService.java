package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
	List<User> findAll();
	Optional<User> findById(Integer id);
	User login(String email, String password);
	void save(User user);
	void delete(Integer id);
}
