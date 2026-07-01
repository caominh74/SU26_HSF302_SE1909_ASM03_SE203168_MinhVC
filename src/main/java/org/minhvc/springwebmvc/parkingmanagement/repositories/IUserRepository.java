package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
	@EntityGraph(attributePaths = "roleID")
	Optional<User> findByEmailIgnoreCase(String email);
}
