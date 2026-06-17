package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.Vehicle;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleImpl implements IVehicleService {
	private final IVehicleRepository vehicleRepository;

	public VehicleImpl(IVehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public List<Vehicle> findAll() {
		try {
			return vehicleRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve vehicles", e);
		}
	}

	@Override
	public Vehicle findById(Integer id) {
		try {
			return vehicleRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve vehicle by id", e);
		}
	}

	@Override
	public void save(Vehicle vehicle) {
		try {
			vehicleRepository.save(vehicle);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save vehicle", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			vehicleRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete vehicle", e);
		}
	}
}