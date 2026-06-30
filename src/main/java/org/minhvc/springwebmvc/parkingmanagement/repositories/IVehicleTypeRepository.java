package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
	Optional<VehicleType> findByTypeName(String typeName);
}
