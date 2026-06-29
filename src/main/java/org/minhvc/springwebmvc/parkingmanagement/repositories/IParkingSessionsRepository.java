package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IParkingSessionsRepository extends JpaRepository<ParkingSessions, Integer> {
	List<ParkingSessions> findByStatus(String status);
	List<ParkingSessions> findByVehicleID_Id(Integer vehicleId);
	List<ParkingSessions> findBySlotID_Id(Integer slotId);

	@Query("SELECT p FROM ParkingSessions p " +
			"JOIN p.vehicleID v JOIN p.slotID s " +
			"WHERE LOWER(v.licensePlate) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			"OR LOWER(s.slotCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			"OR LOWER(p.status) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<ParkingSessions> searchByKeyword(@Param("keyword") String keyword);
}
