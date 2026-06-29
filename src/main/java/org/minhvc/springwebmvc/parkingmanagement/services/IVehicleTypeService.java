package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.VehicleType;

import java.util.List;
import java.util.Optional;

public interface IVehicleTypeService {
	List<VehicleType> findAll();
	Optional<VehicleType> findById(Integer id);
	void save(VehicleType vehicleType);
	void delete(Integer id);
}
