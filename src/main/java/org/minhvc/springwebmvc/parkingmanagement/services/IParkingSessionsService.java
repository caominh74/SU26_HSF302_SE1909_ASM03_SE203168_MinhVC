package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSessions;

import java.util.List;

public interface IParkingSessionsService {
	// Query methods
	List<ParkingSessions> findAll();
	ParkingSessions findById(Integer id);
	List<ParkingSessions> findBySessionId(String sessionId);
	List<ParkingSessions> findByVehicleId(Integer vehicleId);
	List<ParkingSessions> findBySlotId(Integer slotId);
	List<ParkingSessions> findByStatus(String status);

	//Mutation methods
	void save(ParkingSessions parkingSessions);
	void delete(Integer id);
}
