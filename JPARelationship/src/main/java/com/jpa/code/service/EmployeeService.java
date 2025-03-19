package com.jpa.code.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpa.code.dto.EmployeeDTO;
import com.jpa.code.entity.Employee;
import com.jpa.code.exception.DuplicateResourceException;
import com.jpa.code.exception.ResourceNotFoundException;
import com.jpa.code.repository.EmployeeRepo;
import com.jpa.code.utility.ErrorMessages;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo, ModelMapper modelMapper) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
    }

    public void addEmployee(EmployeeDTO empDTO) {
        Employee emp = mapToEmployee(empDTO);
        validateEmp(emp, true);
        employeeRepo.save(emp);
        logger.info("Employee added successfully: ID={}, Name={}", emp.getId(), emp.getName());
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(this::mapToEmployeeDTO) // Map each Employee to EmployeeDTO
                .collect(Collectors.toList());
        }

    public void deleteEmployeeById(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new ResourceNotFoundException(ErrorMessages.EMPLOYEE_NOT_FOUND + id);
        }
        employeeRepo.deleteById(id);
        logger.info("Employee deleted with ID: {}", id);
    }

    public void updateEmployee(EmployeeDTO empDTO, Long id) {
        Employee existingEmp = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.EMPLOYEE_NOT_FOUND + id));

        modelMapper.map(empDTO, existingEmp); // Update fields using ModelMapper
        validateEmp(existingEmp, false);
        employeeRepo.save(existingEmp);
        logger.info("Employee updated successfully: ID={}, Name={}", existingEmp.getId(), existingEmp.getName());
    }

    public Page<Employee> getEmployeesByName(String searchValue, Pageable pageable) {
        if (searchValue == null || searchValue.isEmpty()) {
            return employeeRepo.findAll(pageable);
        }
        return employeeRepo.findByNameContainingIgnoreCaseOrEmpidContainingIgnoreCaseOrJobroleContainingIgnoreCase(
                searchValue, searchValue, searchValue, pageable);
    }

    public Optional<Employee> getEmpById(Long id) {
        return employeeRepo.findById(id);
    }

    private void validateEmp(Employee employee, Boolean isAddType) {
        if (isAddType) {
            if (employeeRepo.existsByName(employee.getName())) {
                throw new DuplicateResourceException(ErrorMessages.DUPLICATE_NAME + employee.getName());
            }
            if (employeeRepo.existsByEmpid(employee.getEmpid())) {
                throw new DuplicateResourceException(ErrorMessages.DUPLICATE_EMPID + employee.getEmpid());
            }
        } else {
            if (employeeRepo.existsByNameAndIdNot(employee.getName(), employee.getId())) {
                throw new DuplicateResourceException(ErrorMessages.DUPLICATE_NAME + employee.getName());
            }
            if (employeeRepo.existsByEmpidAndIdNot(employee.getEmpid(), employee.getId())) {
                throw new DuplicateResourceException(ErrorMessages.DUPLICATE_EMPID + employee.getEmpid());
            }
        }
    }

    private Employee mapToEmployee(EmployeeDTO empDTO) {
        return modelMapper.map(empDTO, Employee.class);
    }

    private EmployeeDTO mapToEmployeeDTO(Employee emp) {
        return modelMapper.map(emp, EmployeeDTO.class);
    }
}