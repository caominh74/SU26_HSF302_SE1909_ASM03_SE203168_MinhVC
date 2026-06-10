package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "ParkingSlots")
public class ParkingSlot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SlotID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ZoneID", nullable = false)
	private Zone zoneID;

	@Nationalized
	@Column(name = "SlotCode", nullable = false, length = 20)
	private String slotCode;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "VehicleTypeID", nullable = false)
	private VehicleType vehicleTypeID;

	@Nationalized
	@Column(name = "Status", nullable = false, length = 20)
	private String status;

	@ColumnDefault("1")
	@Column(name = "IsActive")
	private Boolean isActive;


}