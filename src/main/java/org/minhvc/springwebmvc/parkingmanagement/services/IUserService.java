package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.User;

import java.util.List;

public interface IUserService {
	List<User> findAll();
	User findById(Integer id);
	void save(User user);
	void delete(Integer id);
}