package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.Vehicle;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements IVehicleService {

	@Autowired
	private final IVehicleRepository vehicleRepository;

	public VehicleServiceImpl(IVehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public List<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}

	@Override
	public Optional<Vehicle> findById(Integer id) {
		return vehicleRepository.findById(id);
	}

	@Override
	public Optional<Vehicle> findByLicensePlate(String licensePlate) {
		return vehicleRepository.findByLicensePlate(licensePlate);
	}

	@Override
	public void save(Vehicle vehicle) {
		vehicleRepository.save(vehicle);
	}

	@Override
	public void delete(Integer id) {
		vehicleRepository.deleteById(id);
	}
}
