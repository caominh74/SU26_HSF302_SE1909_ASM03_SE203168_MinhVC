package org.minhvc.springwebmvc.parkingmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sysdiagrams")
public class Sysdiagram {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "diagram_id", nullable = false)
	private Integer id;

	@Column(name = "name", columnDefinition = "sysname not null")
	private Object name;

	@Column(name = "principal_id", nullable = false)
	private Integer principalId;

	@Column(name = "version")
	private Integer version;

	@Column(name = "definition")
	private byte[] definition;


}