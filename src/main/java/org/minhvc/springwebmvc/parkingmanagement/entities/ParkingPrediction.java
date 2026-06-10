package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ParkingPredictions")
public class ParkingPrediction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PredictionID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VehicleTypeID")
	private VehicleType vehicleTypeID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FloorID")
	private Floor floorID;

	@Column(name = "PredictedOccupancyRate", precision = 5, scale = 2)
	private BigDecimal predictedOccupancyRate;

	@Column(name = "PredictedPeakHour")
	private Integer predictedPeakHour;

	@Column(name = "PredictionDate")
	private LocalDate predictionDate;

	@ColumnDefault("getdate()")
	@Column(name = "GeneratedAt")
	private Instant generatedAt;


}