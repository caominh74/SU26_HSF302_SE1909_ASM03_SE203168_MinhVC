package org.minhvc.springwebmvc.parkingmanagement.services;

import org.minhvc.springwebmvc.parkingmanagement.entities.Vehicle;

import java.util.List;

public interface IVehicleService {
	List<Vehicle> findAll();
	Vehicle findById(Integer id);
	void save(Vehicle vehicle);
	void delete(Integer id);
}