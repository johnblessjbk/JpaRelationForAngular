package com.jpa.code.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "Roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 3, message = "Name should have at least 3 characters")
	@Column(length = 20)
	private String name;

	@CreationTimestamp
	@Column(name = "created_date", nullable = false, updatable = false)
	private LocalDateTime createdDate;
}
