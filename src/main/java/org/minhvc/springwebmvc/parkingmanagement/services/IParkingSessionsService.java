package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSessions;

import java.util.List;
import java.util.Optional;

public interface IParkingSessionsService {
	List<ParkingSessions> findAll();
	Optional<ParkingSessions> findById(Integer id);
	List<ParkingSessions> searchByKeyword(String keyword);

	void save(ParkingSessions parkingSession);
	void delete(Integer id);
}
