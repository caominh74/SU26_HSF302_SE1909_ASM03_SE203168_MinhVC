package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "Buildings")
public class Building {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BuildingID", nullable = false)
	private Integer id;

	@Nationalized
	@Column(name = "BuildingName", nullable = false, length = 100)
	private String buildingName;

	@Nationalized
	@Column(name = "Address")
	private String address;

	@Column(name = "TotalFloors")
	private Integer totalFloors;

	@Column(name = "OperatingStartTime")
	private LocalTime operatingStartTime;

	@Column(name = "OperatingEndTime")
	private LocalTime operatingEndTime;

	@ColumnDefault("getdate()")
	@Column(name = "CreatedAt")
	private Instant createdAt;


}