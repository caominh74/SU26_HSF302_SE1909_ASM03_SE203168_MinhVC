package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ParkingSessions")
public class ParkingSessions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SessionID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "VehicleID", nullable = false)
	private Vehicle vehicleID;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "SlotID", nullable = false)
	private ParkingSlot slotID;

	@ColumnDefault("getdate()")
	@Column(name = "EntryTime", nullable = false)
	private LocalDateTime entryTime;

	@Column(name = "ExitTime")
	private LocalDateTime exitTime;

	@Nationalized
	@Column(name = "EntryGate", length = 50)
	private String entryGate;

	@Nationalized
	@Column(name = "ExitGate", length = 50)
	private String exitGate;

	@Nationalized
	@Column(name = "Status", nullable = false, length = 20)
	private String status;

	@Column(name = "EstimatedFee", precision = 10, scale = 2)
	@DecimalMin(value = "0.00", message = "Estimated fee cannot be negative")
	@Digits(integer = 8, fraction = 2, message = "Estimated fee must be at most 99,999,999.99 with no more than 2 decimal places")
	private BigDecimal estimatedFee;

	@Column(name = "FinalFee", precision = 10, scale = 2)
	@DecimalMin(value = "0.00", message = "Final fee cannot be negative")
	@Digits(integer = 8, fraction = 2, message = "Final fee must be at most 99,999,999.99 with no more than 2 decimal places")
	private BigDecimal finalFee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CreatedBy")
	private User customerUser;
}
