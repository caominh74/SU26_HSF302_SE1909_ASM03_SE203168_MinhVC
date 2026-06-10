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
@Table(name = "IncidentReports")
public class IncidentReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IncidentID", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SessionID")
	private ParkingSessions sessionID;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ReportedBy", nullable = false)
	private User reportedBy;

	@Nationalized
	@Column(name = "IncidentType", length = 50)
	private String incidentType;

	@Nationalized
	@Column(name = "Description", length = 500)
	private String description;

	@Nationalized
	@Column(name = "Status", length = 20)
	private String status;

	@ColumnDefault("getdate()")
	@Column(name = "CreatedAt")
	private Instant createdAt;


}