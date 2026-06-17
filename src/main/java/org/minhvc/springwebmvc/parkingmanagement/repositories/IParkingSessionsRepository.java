package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSessions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IParkingSessionsRepository extends JpaRepository<ParkingSessions, Integer> {
	List<ParkingSessions> findByStatus(String status);
	List<ParkingSessions> findByVehicleID_Id(Integer vehicleId);
	List<ParkingSessions> findBySlotID_Id(Integer slotId);
}