package com.vivekSpringBoot.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekSpringBoot.shopping.model.UserDtls;

public interface UserDtlsRepo extends JpaRepository<UserDtls, Integer> {

	public UserDtls findByEmail(String email);
	
	public List<UserDtls> findByRole(String role);
	
	public UserDtls findById(int id);
	
	public UserDtls findByPassResetToken(String resetToken);
	
}
