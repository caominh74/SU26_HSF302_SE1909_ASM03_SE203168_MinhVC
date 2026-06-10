package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "Roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RoleID", nullable = false)
	private Integer id;

	@Nationalized
	@Column(name = "RoleName", nullable = false, length = 50)
	private String roleName;

	@Nationalized
	@Column(name = "Description")
	private String description;


}