package com.jpa.code.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.code.entity.UserDetails;

@Repository
public interface UserRepo extends JpaRepository<UserDetails, Long> {
	boolean existsByName(String name);

	boolean existsByEmail(String email);

	List<UserDetails> findByNameContainingIgnoreCase(String name);

	@Query(value = "SELECT u FROM UserDetails u left join u.address a left join u.roles r"
			+ " WHERE u.name LIKE %:val% OR u.email LIKE %:val% OR u.phone LIKE %:val% or"
			+ " r.name like %:val% or a.city like %:val%")
	Page<UserDetails> searchUsers(@Param("val") String val,Pageable pageable);
	
    Optional<UserDetails> findById(Long id);


}
