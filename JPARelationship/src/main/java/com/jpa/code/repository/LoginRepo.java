package com.jpa.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.code.entity.login;

@Repository
public interface LoginRepo extends JpaRepository<login, Long> {

}
