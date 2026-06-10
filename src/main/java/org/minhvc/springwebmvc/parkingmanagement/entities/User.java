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
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserID", nullable = false)
	private Integer id;

	@Nationalized
	@Column(name = "FullName", nullable = false, length = 100)
	private String fullName;

	@Nationalized
	@Column(name = "Email", nullable = false, length = 100)
	private String email;

	@Nationalized
	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Nationalized
	@Column(name = "PasswordHash", nullable = false)
	private String passwordHash;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "RoleID", nullable = false)
	private Role roleID;

	@ColumnDefault("1")
	@Column(name = "IsActive")
	private Boolean isActive;

	@ColumnDefault("getdate()")
	@Column(name = "CreatedAt")
	private Instant createdAt;


}