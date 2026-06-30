package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.VehicleType;
import org.minhvc.springwebmvc.parkingmanagement.repositories.IVehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeServiceImpl implements IVehicleTypeService {

	@Autowired
	private final IVehicleTypeRepository vehicleTypeRepository;

	public VehicleTypeServiceImpl(IVehicleTypeRepository vehicleTypeRepository) {
		this.vehicleTypeRepository = vehicleTypeRepository;
	}

	@Override
	public List<VehicleType> findAll() {
		return vehicleTypeRepository.findAll();
	}

	@Override
	public Optional<VehicleType> findById(Integer id) {
		return vehicleTypeRepository.findById(id);
	}

	@Override
	public Optional<VehicleType> findByTypeName(String typeName) {
		return vehicleTypeRepository.findByTypeName(typeName);
	}

	@Override
	public void save(VehicleType vehicleType) {
		vehicleTypeRepository.save(vehicleType);
	}

	@Override
	public void delete(Integer id) {
		vehicleTypeRepository.deleteById(id);
	}
}
