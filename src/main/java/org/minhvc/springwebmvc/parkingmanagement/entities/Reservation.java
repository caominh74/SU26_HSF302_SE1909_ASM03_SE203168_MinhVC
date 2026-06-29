package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime reservationStart;

	@Column(name = "ReservationEnd", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime reservationEnd;

	@Nationalized
	@Column(name = "Status", length = 20)
	private String status;

	@ColumnDefault("getdate()")
	@Column(name = "CreatedAt")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", userID=" + userID +
				", vehicleTypeID=" + vehicleTypeID +
				", slotID=" + slotID +
				", reservationStart=" + reservationStart +
				", reservationEnd=" + reservationEnd +
				", status='" + status + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}
