package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "Zones")
public class Zone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ZoneID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "FloorID", nullable = false)
	private Floor floorID;

	@Nationalized
	@Column(name = "ZoneName", nullable = false, length = 50)
	private String zoneName;

	@Nationalized
	@Column(name = "Description")
	private String description;


}