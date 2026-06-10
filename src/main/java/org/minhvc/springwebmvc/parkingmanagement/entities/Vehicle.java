package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "Vehicles")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VehicleID", nullable = false)
	private Integer id;

	@Nationalized
	@Column(name = "LicensePlate", nullable = false, length = 20)
	private String licensePlate;

	@Nationalized
	@Column(name = "OwnerName", length = 100)
	private String ownerName;

	@Nationalized
	@Column(name = "OwnerPhone", length = 20)
	private String ownerPhone;


}