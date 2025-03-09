package com.jpa.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.code.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

	boolean existsByName(String name);

	boolean existsByEmpid(String empid);

}
