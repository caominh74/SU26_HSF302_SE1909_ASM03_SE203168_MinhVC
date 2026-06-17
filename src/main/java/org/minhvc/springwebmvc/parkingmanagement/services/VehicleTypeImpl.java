package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.VehicleType;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IVehicleTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTypeImpl implements IVehicleTypeService {
	private final IVehicleTypeRepository vehicleTypeRepository;

	public VehicleTypeImpl(IVehicleTypeRepository vehicleTypeRepository) {
		this.vehicleTypeRepository = vehicleTypeRepository;
	}

	@Override
	public List<VehicleType> findAll() {
		try {
			return vehicleTypeRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve vehicle types", e);
		}
	}

	@Override
	public VehicleType findById(Integer id) {
		try {
			return vehicleTypeRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve vehicle type by id", e);
		}
	}

	@Override
	public void save(VehicleType vehicleType) {
		try {
			vehicleTypeRepository.save(vehicleType);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save vehicle type", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			vehicleTypeRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete vehicle type", e);
		}
	}
}