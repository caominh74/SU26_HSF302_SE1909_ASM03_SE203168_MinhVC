package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {
	@Query("SELECT slot FROM ParkingSlot slot " +
			"WHERE slot.isActive = true " +
			"AND slot.status = 'AVAILABLE' " +
			"AND NOT EXISTS (" +
			"SELECT session.id FROM ParkingSessions session " +
			"WHERE session.slotID = slot AND session.status = 'PARKING') " +
			"ORDER BY slot.slotCode")
	List<ParkingSlot> findAvailableForParking();
}
