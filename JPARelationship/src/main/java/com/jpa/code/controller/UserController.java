package com.jpa.code.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.code.dto.UserResponse;
import com.jpa.code.dto.UserWithRolesRequest;
import com.jpa.code.entity.Role;
import com.jpa.code.entity.UserDetails;
import com.jpa.code.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/adduser")
	public ResponseEntity<?> insertUserDetails(@Valid @RequestBody UserDetails user, BindingResult result) {
		userService.insertUserData(user);
		return ResponseEntity.ok("User added successfully");
	}

	@PostMapping("/addRole")
	public ResponseEntity<?> insertRole(@Valid @RequestBody Role role, BindingResult result) {
		userService.addRoles(role);
		return ResponseEntity.ok("Role Added successfully");
	}

	@GetMapping("getRolelist/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable("id") long id) {
		return ResponseEntity.ok(userService.getRoleById(id));
	}
	@GetMapping("/Rolelist")
	public ResponseEntity<List<Role>> getAllRole() {
		return ResponseEntity.ok(userService.getAllRoleList());
	}
	@PostMapping("/insertUser")
	public ResponseEntity<?> withRole_Data(@Valid @RequestBody UserWithRolesRequest user, BindingResult result) {
		return ResponseEntity.ok(userService.insertUser(user));
	}

	@GetMapping("/get-alluser")
	public ResponseEntity<List<UserResponse>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUsers());

	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		return userService.getUserDatabyId(id).map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/Usersearch")
	public ResponseEntity<Page<UserDetails>> searchUsersByName(
			@RequestParam(name = "searchValue", required = false) String val,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<UserDetails> users = userService.getUsersByName(val, pageable);
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(users);
		}
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("User with ID " + id + " has been deleted.");
	}

	@PutMapping("EditUser/{id}")
	public ResponseEntity<String> putMethodName(@PathVariable("id") Long id,
			@Valid @RequestBody UserDetails userDetails) {
		Optional<UserDetails> user = userService.updateUserById(id, userDetails);
		if (user.isPresent()) {
			return ResponseEntity.ok("User id " + id + " Updated succesfully");
		}
		return ResponseEntity.noContent().build();
	}
}