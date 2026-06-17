package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSlot;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IParkingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotImpl implements IParkingSlotService {
	private final IParkingSlotRepository parkingSlotRepository;

	public ParkingSlotImpl(IParkingSlotRepository parkingSlotRepository) {
		this.parkingSlotRepository = parkingSlotRepository;
	}

	@Override
	public List<ParkingSlot> findAll() {
		try {
			return parkingSlotRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve parking slots", e);
		}
	}

	@Override
	public ParkingSlot findById(Integer id) {
		try {
			return parkingSlotRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve parking slot by id", e);
		}
	}

	@Override
	public void save(ParkingSlot parkingSlot) {
		try {
			parkingSlotRepository.save(parkingSlot);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save parking slot", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			parkingSlotRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete parking slot", e);
		}
	}
}