package com.jpa.code.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;
@Data
@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty(message = "Name should not be empty")
	@Size(min = 3,max = 15, message = "Minimum char should be 3 and Max is 15")
	private String name;
	@NotEmpty(message = "Emploee Id should not empty")
	private String empid;
	@NotEmpty(message = "Job Role should not be empty")
	private String jobrole;
}
