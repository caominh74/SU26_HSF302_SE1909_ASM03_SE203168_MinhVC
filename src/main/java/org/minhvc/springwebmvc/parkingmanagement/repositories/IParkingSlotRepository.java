package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {

}
