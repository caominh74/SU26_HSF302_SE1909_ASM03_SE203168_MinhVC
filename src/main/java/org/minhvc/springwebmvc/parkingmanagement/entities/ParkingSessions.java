package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ParkingSessions")
public class ParkingSessions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SessionID", nullable = false)
	private Integer id;

	@NotNull(message = "Vehicle is required")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "VehicleID", nullable = false)
	private Vehicle vehicleID;

	@NotNull(message = "Parking Slot is required")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "SlotID", nullable = false)
	private ParkingSlot slotID;

	@NotNull(message = "Entry Time is required")
	@ColumnDefault("getdate()")
	@Column(name = "EntryTime", nullable = false)
	private Instant entryTime;

	@Column(name = "ExitTime")
	private Instant exitTime;

	@Size(max = 50, message = "Entry Gate cannot exceed 50 characters")
	@Nationalized
	@Column(name = "EntryGate", length = 50)
	private String entryGate;

	@Size(max = 50, message = "Exit Gate cannot exceed 50 characters")
	@Nationalized
	@Column(name = "ExitGate", length = 50)
	private String exitGate;

	@NotBlank(message = "Status is required")
	@Size(max = 20, message = "Status cannot exceed 20 characters")
	@Nationalized
	@Column(name = "Status", nullable = false, length = 20)
	private String status;

	@Column(name = "EstimatedFee", precision = 10, scale = 2)
	private BigDecimal estimatedFee;

	@Column(name = "FinalFee", precision = 10, scale = 2)
	private BigDecimal finalFee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CreatedBy")
	private User createdBy;


}