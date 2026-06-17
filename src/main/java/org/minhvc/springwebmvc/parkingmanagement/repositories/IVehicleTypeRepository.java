package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVehicleTypeRepository extends JpaRepository<VehicleType, Integer> {

}