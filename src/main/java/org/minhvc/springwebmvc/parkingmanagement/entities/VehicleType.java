package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "VehicleTypes")
public class VehicleType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VehicleTypeID", nullable = false)
	private Integer id;

	@Nationalized
	@Column(name = "TypeName", nullable = false, length = 50)
	private String typeName;

	@Nationalized
	@Column(name = "Description")
	private String description;


}