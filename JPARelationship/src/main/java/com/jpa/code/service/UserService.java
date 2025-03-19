package com.jpa.code.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jpa.code.dto.UserResponse;
import com.jpa.code.dto.UserWithRolesRequest;
import com.jpa.code.entity.Address;
import com.jpa.code.entity.Role;
import com.jpa.code.entity.UserDetails;
import com.jpa.code.exception.ResourceNotFoundException;
import com.jpa.code.repository.AddressRepo;
import com.jpa.code.repository.LoginRepo;
import com.jpa.code.repository.RoleRepo;
import com.jpa.code.repository.UserRepo;
import com.jpa.code.utility.ErrorMessages;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private LoginRepo loginRepo;

	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private RoleRepo roleRepo;


	public void insertUserData(UserDetails user) {
		validateUser(user);

		userRepo.save(user);

		// Set UserDetails entity for login
		user.getLogin().setUser(user);
		loginRepo.save(user.getLogin());
		roleRepo.saveAll(user.getRoles());
		List<Address> addressList = user.getAddress();
		if (addressList != null) {
			for (Address address : addressList) {
				address.setUser(user); // Set the user for each address
			}
			addressRepo.saveAll(addressList);
		}
	}

	public void addRoles(Role roles) {
		roleRepo.save(roles);
	}

	public String insertUser(UserWithRolesRequest user) {

		UserDetails userDetails = new UserDetails();
		userDetails.setName(user.getName());
		userDetails.setEmail(user.getEmail());
		userDetails.setPhone(user.getPhone());
		userDetails.setAge(user.getAge());
		userDetails.setPassword(user.getPassword());

		Set<Role> roles = new HashSet<>();
		for (Long roleId : user.getRoleIds()) {
			Optional<Role> roleOptional = roleRepo.findById(roleId);
			if (roleOptional.isPresent()) {
				roles.add(roleOptional.get());
			} else {
				return "Role with ID " + roleId + " not found";
			}
		}
		validateUser(userDetails);

		userDetails.setRoles(roles);
		userRepo.save(userDetails);
		return "User Added with Role";
	}

	public List<UserResponse> getAllUsers() {
		List<UserDetails> userDetailsList = userRepo.findAll();
		return userDetailsList.stream().map(this::convertToUserResponse).collect(Collectors.toList());
	}
public List<Role> getAllRoleList(){
	return roleRepo.findAll();
}
	private UserResponse convertToUserResponse(UserDetails userDetails) {
		UserResponse userResponse = new UserResponse();
		userResponse.setId(userDetails.getId());
		userResponse.setName(userDetails.getName());
		userResponse.setEmail(userDetails.getEmail());
		userResponse.setPhone(userDetails.getPhone());
		userResponse.setAge(userDetails.getAge());
		userResponse.setAddress(userDetails.getAddress());
		userResponse.setLogin(userDetails.getLogin());
		Set<String> roleNames = userDetails.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
		userResponse.setRoles(roleNames);
		return userResponse;
	}

	public Optional<UserDetails> getUserDatabyId(Long id) {
		return userRepo.findById(id);
	}

	private void validateUser(UserDetails user) {
		if (userRepo.existsByName(user.getName())) {
			throw new IllegalArgumentException("UserName: Username is already taken!");
		}
		if (userRepo.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email: Email is already in use!");
		}
	}

	public Page<UserDetails> getUsersByName(String searchValue, Pageable pageable) {
		if (searchValue == null || searchValue.isEmpty()) {
			return userRepo.findAll(pageable);
		}
		return userRepo.searchUsers(searchValue, pageable);
	}

	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}

	public Optional<UserDetails> updateUserById(Long id, UserDetails updatedUserDetails) {
		Optional<UserDetails> optionalUser = userRepo.findById(id);
		if (optionalUser.isPresent()) {
			validateUser(updatedUserDetails);
			UserDetails user = optionalUser.get();
			user.setName(updatedUserDetails.getName());
			user.setEmail(updatedUserDetails.getEmail());
			user.setPhone(updatedUserDetails.getPhone());
			user.setAge(updatedUserDetails.getAge());
			user.setPassword(updatedUserDetails.getPassword());
			// Update other fields as needed
			return Optional.of(userRepo.save(user));
		} else {
			return Optional.empty();
		}
	}

	public Role getRoleById(long id) {
		return roleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND+id));
	}
}