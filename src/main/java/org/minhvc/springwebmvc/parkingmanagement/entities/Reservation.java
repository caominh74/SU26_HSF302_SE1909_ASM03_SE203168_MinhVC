package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "Reservations")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReservationID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "UserID", nullable = false)
	private User userID;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "VehicleTypeID", nullable = false)
	private VehicleType vehicleTypeID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SlotID")
	private ParkingSlot slotID;

	@Column(name = "ReservationStart", nullable = false)
	private Instant reservationStart;

	@Column(name = "ReservationEnd", nullable = false)
	private Instant reservationEnd;

	@Nationalized
	@Column(name = "Status", length = 20)
	private String status;

	@ColumnDefault("getdate()")
	@Column(name = "CreatedAt")
	private Instant createdAt;


}