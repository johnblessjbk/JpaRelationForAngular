package com.jpa.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.code.entity.Address;
@Repository
public interface AddressRepo extends JpaRepository<Address, Long>{

}
