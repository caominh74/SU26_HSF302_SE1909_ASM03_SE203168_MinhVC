package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSessions;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IParkingSessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingSessionsServiceImpl implements IParkingSessionsService {

	@Autowired
	private final IParkingSessionsRepository parkingSessionsRepository;

	public ParkingSessionsServiceImpl(IParkingSessionsRepository parkingSessionsRepository) {
		this.parkingSessionsRepository = parkingSessionsRepository;
	}

	@Override
	public List<ParkingSessions> findAll() {
		try {
			return parkingSessionsRepository.findAll();
		} catch (Exception exception) {
			throw new RuntimeException("Failed to retrieve parking sessions", exception);
		}
	}

	@Override
	public Optional<ParkingSessions> findById(Integer id) {
		try {
			return parkingSessionsRepository.findById(id);
		} catch (Exception exception) {
			throw new RuntimeException("Failed to retrieve parking session", exception);
		}
	}

	@Override
	public List<ParkingSessions> searchByKeyword(String keyword) {
		try {
			if (keyword == null || keyword.isBlank()) {
				return parkingSessionsRepository.findAll();
			}
			return parkingSessionsRepository.searchByKeyword(keyword.trim());
		} catch (Exception exception) {
			throw new RuntimeException("Failed to search parking sessions", exception);
		}
	}

	@Override
	public void save(ParkingSessions parkingSession) {
		try {
			if (parkingSession.getId() == null) {
				if (parkingSession.getEntryTime() == null) {
					parkingSession.setEntryTime(LocalDateTime.now());
				}
				if (parkingSession.getStatus() == null || parkingSession.getStatus().isBlank()) {
					parkingSession.setStatus("PARKING");
				}
				parkingSessionsRepository.save(parkingSession);
				return;
			}

			ParkingSessions existingSession = parkingSessionsRepository
					.findById(parkingSession.getId())
					.orElse(null);
			if (existingSession == null) {
				return;
			}

			existingSession.setVehicleID(parkingSession.getVehicleID());
			existingSession.setSlotID(parkingSession.getSlotID());
			existingSession.setEntryTime(parkingSession.getEntryTime());
			existingSession.setExitTime(parkingSession.getExitTime());
			existingSession.setEntryGate(parkingSession.getEntryGate());
			existingSession.setExitGate(parkingSession.getExitGate());
			existingSession.setStatus(parkingSession.getStatus());
			existingSession.setEstimatedFee(parkingSession.getEstimatedFee());
			existingSession.setFinalFee(parkingSession.getFinalFee());
			existingSession.setCustomerUser(parkingSession.getCustomerUser());
			parkingSessionsRepository.save(existingSession);
		} catch (Exception exception) {
			throw new RuntimeException("Failed to save parking session", exception);
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			parkingSessionsRepository.deleteById(id);
		} catch (Exception exception) {
			throw new RuntimeException("Failed to delete parking session", exception);
		}
	}
}
