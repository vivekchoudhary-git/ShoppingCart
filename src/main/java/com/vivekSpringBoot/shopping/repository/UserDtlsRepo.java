package com.vivekSpringBoot.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekSpringBoot.shopping.model.UserDtls;

public interface UserDtlsRepo extends JpaRepository<UserDtls, Integer> {

	public UserDtls findByEmail(String email);
	
}
