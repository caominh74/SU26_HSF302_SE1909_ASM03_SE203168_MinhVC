package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSlot;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSlotServiceImpl implements IParkingSlotService {

	@Autowired
	private final IParkingSlotRepository parkingSlotRepository;

	public ParkingSlotServiceImpl(IParkingSlotRepository parkingSlotRepository) {
		this.parkingSlotRepository = parkingSlotRepository;
	}

	@Override
	public List<ParkingSlot> findAll() {
		return parkingSlotRepository.findAll();
	}

	@Override
	public List<ParkingSlot> findAvailableForParking() {
		return parkingSlotRepository.findAvailableForParking();
	}

	@Override
	public Optional<ParkingSlot> findById(Integer id) {
		return parkingSlotRepository.findById(id);
	}

	@Override
	public void save(ParkingSlot parkingSlot) {
		parkingSlotRepository.save(parkingSlot);
	}

	@Override
	public void delete(Integer id) {
		parkingSlotRepository.deleteById(id);
	}
}
