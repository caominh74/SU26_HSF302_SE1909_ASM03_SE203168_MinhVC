package org.minhvc.springwebmvc.parkingmanagement.repositories;

import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSessions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParkingSessionsRepository extends JpaRepository<ParkingSessions, Integer> {

}
