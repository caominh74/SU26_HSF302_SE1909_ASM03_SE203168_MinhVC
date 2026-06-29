package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSlot;

import java.util.List;
import java.util.Optional;

public interface IParkingSlotService {
	List<ParkingSlot> findAll();
	Optional<ParkingSlot> findById(Integer id);
	void save(ParkingSlot parkingSlot);
	void delete(Integer id);
}
