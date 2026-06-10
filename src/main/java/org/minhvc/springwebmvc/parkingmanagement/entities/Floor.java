package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "Floors")
public class Floor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FloorID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "BuildingID", nullable = false)
	private Building buildingID;

	@Column(name = "FloorNumber", nullable = false)
	private Integer floorNumber;

	@Nationalized
	@Column(name = "FloorName", length = 50)
	private String floorName;


}