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
@Table(name = "Payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "SessionID", nullable = false)
	private ParkingSessions sessionID;

	@Column(name = "Amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	@Nationalized
	@Column(name = "PaymentMethod", length = 30)
	private String paymentMethod;

	@Nationalized
	@Column(name = "PaymentStatus", length = 20)
	private String paymentStatus;

	@Column(name = "PaidAt")
	private Instant paidAt;


}