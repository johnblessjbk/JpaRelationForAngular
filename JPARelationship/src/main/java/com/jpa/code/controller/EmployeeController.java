package com.jpa.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.code.ResponseEntity.ApiResponse;
import com.jpa.code.dto.EmployeeDTO;
import com.jpa.code.service.EmployeeService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addEmployee(@Valid @RequestBody EmployeeDTO empDTO) {
        employeeService.addEmployee(empDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("success", "Employee added successfully", null));
   
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(new ApiResponse<>("success", "Employees retrieved successfully", employees));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteEmployeeById(@PathVariable("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Employee deleted successfully", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateEmployee(@Valid @RequestBody EmployeeDTO empDTO, @PathVariable("id") Long id) {
        employeeService.updateEmployee(empDTO, id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Employee updated successfully", id));
    }
}