package com.jpa.code.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.code.dto.EmployeeDTO;
import com.jpa.code.entity.Employee;
import com.jpa.code.repository.EmployeeRepo;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepo employeeRepo;

    public void addEmployee(EmployeeDTO empDTO) {
        Employee emp = mapToEmployee(empDTO); // Manually map DTO to Entity
        validateEmp(emp);
        employeeRepo.save(emp);
        logger.info("Employee added successfully: {}", emp.getName());
    }

    private void validateEmp(Employee employee) {
        if (employeeRepo.existsByName(employee.getName())) {
            throw new IllegalArgumentException("Employee name already exists: " + employee.getName());
        }
        if (employeeRepo.existsByEmpid(employee.getEmpid())) {
            throw new IllegalArgumentException("Employee ID already exists: " + employee.getEmpid());
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(this::mapToEmployeeDTO) // Manually map Entity to DTO
                .collect(Collectors.toList());
    }

    public void deleteEmployeeById(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        employeeRepo.deleteById(id);
        logger.info("Employee deleted with ID: {}", id);
    }

    public void updateEmployee(EmployeeDTO empDTO, Long id) {
        Employee existingEmp = employeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        
        // Manually update fields from DTO to Entity
        existingEmp.setEmpid(empDTO.getEmpid());
        existingEmp.setName(empDTO.getName());
        existingEmp.setJobrole(empDTO.getJobrole());

        validateEmp(existingEmp); // Validate the updated employee
        employeeRepo.save(existingEmp);

        logger.info("Employee updated successfully: {}", id);
    }

    // Helper method to map EmployeeDTO to Employee
    private Employee mapToEmployee(EmployeeDTO empDTO) {
        Employee emp = new Employee();
        emp.setEmpid(empDTO.getEmpid());
        emp.setName(empDTO.getName());
        emp.setJobrole(empDTO.getJobrole());
        return emp;
    }

    // Helper method to map Employee to EmployeeDTO
    private EmployeeDTO mapToEmployeeDTO(Employee emp) {
        EmployeeDTO empDTO = new EmployeeDTO();
        empDTO.setId(emp.getId());
        empDTO.setEmpid(emp.getEmpid());
        empDTO.setName(emp.getName());
        empDTO.setJobrole(emp.getJobrole());
        return empDTO;
    }
}