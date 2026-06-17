package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.VehicleType;

import java.util.List;

public interface IVehicleTypeService {
	List<VehicleType> findAll();
	VehicleType findById(Integer id);
	void save(VehicleType vehicleType);
	void delete(Integer id);
}