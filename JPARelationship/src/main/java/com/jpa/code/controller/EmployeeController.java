package com.jpa.code.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.code.ResponseEntity.ApiResponse;
import com.jpa.code.dto.EmployeeDTO;
import com.jpa.code.entity.Employee;
import com.jpa.code.entity.UserDetails;
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
	@GetMapping("/getEmpList")
	public ResponseEntity<Page<Employee>> searchUsersByName(
			@RequestParam(name = "searchValue", required = false) String val,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Employee> emps = employeeService.getEmployeesByName(val, pageable);
		if (emps.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(emps);
		}
	}
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable("id") Long id) {
       
        return ResponseEntity.ok( employeeService.getEmpById(id));
    }
}