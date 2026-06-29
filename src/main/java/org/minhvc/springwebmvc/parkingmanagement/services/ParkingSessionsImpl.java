package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSessions;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IParkingSessionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSessionsImpl implements IParkingSessionsService {
	private final IParkingSessionsRepository parkingSessionRepository;

	public ParkingSessionsImpl(IParkingSessionsRepository parkingSessionRepository) {
		this.parkingSessionRepository = parkingSessionRepository;
	}

	@Override
	public List<ParkingSessions> findAll() {
		try {
			return parkingSessionRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve parking sessions", e);
		}
	}

	@Override
	public ParkingSessions findById(Integer id) {
		try {
			return parkingSessionRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve parking session by id", e);
		}
	}

	@Override
	public List<ParkingSessions> findBySessionId(String sessionId) {
		return List.of();
	}

	@Override
	public List<ParkingSessions> findByVehicleId(Integer vehicleId) {
		try {
			return parkingSessionRepository.findByVehicleID_Id(vehicleId);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve parking sessions by vehicle id", e);
		}
	}

	@Override
	public List<ParkingSessions> findBySlotId(Integer slotId) {
		try {
			return parkingSessionRepository.findBySlotID_Id(slotId);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve parking sessions by slot id", e);
		}
	}

	@Override
	public List<ParkingSessions> findByStatus(String status) {
		try {
			return parkingSessionRepository.findByStatus(status);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve parking sessions by status", e);
		}
	}

	@Override
	public List<ParkingSessions> searchByKeyword(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return parkingSessionRepository.findAll();
		}
		return parkingSessionRepository.searchByKeyword(keyword.trim());
	}

	@Override
	public void save(ParkingSessions parkingSessions) {
		try {
			if (parkingSessions.getId() == null) {
				parkingSessionRepository.save(parkingSessions);
			} else {
				var existItem = parkingSessionRepository.findById(parkingSessions.getId()).orElse(null);
				if (existItem != null) {
					existItem.setId(parkingSessions.getId());
					existItem.setVehicleID(parkingSessions.getVehicleID());
					existItem.setSlotID(parkingSessions.getSlotID());
					existItem.setEntryTime(parkingSessions.getEntryTime());
					existItem.setExitTime(parkingSessions.getExitTime());
					existItem.setEntryGate(parkingSessions.getEntryGate());
					existItem.setExitGate(parkingSessions.getExitGate());
					existItem.setStatus(parkingSessions.getStatus());
					existItem.setEstimatedFee(parkingSessions.getEstimatedFee());
					existItem.setFinalFee(parkingSessions.getFinalFee());
					existItem.setCreatedBy(parkingSessions.getCreatedBy());
					parkingSessionRepository.save(existItem);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to save parking session", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			parkingSessionRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete parking session", e);
		}
	}
}
