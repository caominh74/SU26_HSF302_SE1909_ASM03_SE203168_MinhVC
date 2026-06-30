package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVehicleRepository extends JpaRepository<Vehicle, Integer> {
	Optional<Vehicle> findByLicensePlate(String licensePlate);
}
