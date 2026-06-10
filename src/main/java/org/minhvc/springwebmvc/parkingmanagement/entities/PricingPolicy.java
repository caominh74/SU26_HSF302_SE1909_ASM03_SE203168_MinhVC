package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "PricingPolicies")
public class PricingPolicy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PricingPolicyID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "VehicleTypeID", nullable = false)
	private VehicleType vehicleTypeID;

	@Nationalized
	@Column(name = "PolicyName", length = 100)
	private String policyName;

	@Column(name = "BasePrice", nullable = false, precision = 10, scale = 2)
	private BigDecimal basePrice;

	@Column(name = "PricePerHour", nullable = false, precision = 10, scale = 2)
	private BigDecimal pricePerHour;

	@Column(name = "MaxDailyRate", precision = 10, scale = 2)
	private BigDecimal maxDailyRate;

	@Column(name = "LostTicketFee", precision = 10, scale = 2)
	private BigDecimal lostTicketFee;

	@Column(name = "OvertimeFeePerHour", precision = 10, scale = 2)
	private BigDecimal overtimeFeePerHour;

	@Column(name = "EffectiveFrom", nullable = false)
	private Instant effectiveFrom;

	@Column(name = "EffectiveTo")
	private Instant effectiveTo;


}