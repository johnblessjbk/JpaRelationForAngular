package com.jpa.code.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.code.entity.Employee;
import com.jpa.code.entity.UserDetails;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

	boolean existsByName(String name);

	boolean existsByEmpid(String empid);

    Page<Employee> findByNameContainingIgnoreCaseOrEmpidContainingIgnoreCaseOrJobroleContainingIgnoreCase(String name, String empid, String jobrole, Pageable pageable);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.name = :name AND e.id <> :id")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);


    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.empid = :empid AND e.id <> :id")
    boolean existsByEmpidAndIdNot(@Param("empid") String empid, @Param("id") Long id);
}
